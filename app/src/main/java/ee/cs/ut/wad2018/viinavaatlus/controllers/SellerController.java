package ee.cs.ut.wad2018.viinavaatlus.controllers;

import ee.cs.ut.wad2018.viinavaatlus.dtos.SellerDTO;
import ee.cs.ut.wad2018.viinavaatlus.entities.Seller;
import ee.cs.ut.wad2018.viinavaatlus.entities.SellerImage;
import ee.cs.ut.wad2018.viinavaatlus.repositories.SellerImageRepository;
import ee.cs.ut.wad2018.viinavaatlus.repositories.SellerRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.security.Principal;
import java.util.Optional;

/**
 * Controller that handles the requests for {@link Seller} pages.
 */
@Slf4j
@Controller
@RequestMapping("sellers")
public class SellerController {

    private final SellerRepository sellerRepository;
    private final SellerImageRepository sellerImageRepository;

    /** Constructor
     */
    SellerController(SellerRepository sellerRepository, SellerImageRepository sellerImageRepository) {
        this.sellerRepository = sellerRepository;
        this.sellerImageRepository = sellerImageRepository;
    }

    // For testing, won't be used in production.
    @GetMapping()
    public String showAllSellers(Model model) {
        model.addAttribute("sellers", sellerRepository.findAll());
        model.addAttribute("sellerCount", sellerRepository.countSellers());
        return "sellers/all";
    }

    // For testing, won't be used in production.
    @GetMapping(path = "test")
    public String testForm() {
        return "sellers/testForm";
    }

    /**
     * Returns the image associated with the specified {@link Seller}.
     * @param id Id of the Seller.
     * @return On success returns a HTTP 200 (OK) response, with a byte[] body. Content-Type header contains the
     * type of the image. Content-Disposition is set to "inline" and contains the filename of the image.
     */
    @GetMapping(path = "{id}/image")
    public ResponseEntity<byte[]> getImage(@PathVariable Long id) {
        Optional<SellerImage> image = sellerImageRepository.findBySellerId(id);
        if (!image.isPresent()) {
            return ResponseEntity.notFound().build(); // FIXME: This doesn't return the /error page.
        }
        HttpHeaders headers = new HttpHeaders();
        if (image.isPresent() && !image.get().getFileName().isEmpty()) {
            headers.setContentDisposition(ContentDisposition.builder("inline")
                    .filename(image.get().getFileName()).build());
        }
        headers.setContentType(MediaType.parseMediaType(image.map(SellerImage::getFileType).orElse("")));

        return new ResponseEntity<>(
                image.map(SellerImage::getFileBody).orElse(null),
                headers,
                HttpStatus.OK
        );
    }

    /**
     * Returns the detail view of a {@link Seller}
     * @param id Id of the seller.
     * @param model Model, to send attributes to the thymeleaf template.
     * @return If the seller exists, returns the detail view page to the user.
     */
    @GetMapping(path = "{id}")
    public String getDetails(@PathVariable Long id, Model model) {
        Optional<Seller> entity = sellerRepository.findById(id);
        if (!entity.isPresent()) {
            return "redirect:/"; //TODO: Show error message here.
        }
        model.addAttribute("seller", new SellerDTO(entity.get()));
        return "sellers/details";
    }

    /**
     * Returns the form to create a new {@link Seller} to the user.
     * <p>
     * If the user already has created a seller, they are redirected to the landing page.
     * (Will be changed to their sellers detail page.)
     * @param principal Principal of the logged in user.
     * @return If the user doesn't own a seller, returns the create form page to the user.
     */
    @GetMapping(path = "create")
    public String getCreateForm(Principal principal) {
        if (principal instanceof OAuth2AuthenticationToken) {
            OAuth2AuthenticationToken auth = (OAuth2AuthenticationToken) principal;
            if (sellerRepository.existsByOwnerId((String) auth.getPrincipal().getAttributes().get("email"))) {
                return "redirect:/"; // TODO: Redirect to owned seller detail page
            }
        }

        return "sellers/create";
    }

    /**
     * Creates a new {@link Seller} from input data.
     * <p>
     * If the logged in user that made the request already has a seller, they are redirected to the landing page
     * (Will be to their seller detail page in the future).
     * @param data Data of the seller.
     * @param principal Pricipal of the logged in user.
     * @return If successful, user is redirected to the detail page of the seller they just created.
     */
    @PostMapping()
    public String createNewSeller(@ModelAttribute SellerDTO data, Principal principal) {
        Seller entity = new Seller(data);
        SellerImage imageEntity;
        try {
            imageEntity = new SellerImage(data.getImageFile());
        } catch (IOException e) {
            log.warn("Failed to create a SellerImage");
            return "redirect:/sellers"; // TODO: Redirect back to referrer here.
        }

        if (principal instanceof OAuth2AuthenticationToken) {
            OAuth2AuthenticationToken auth = (OAuth2AuthenticationToken) principal;
            if (sellerRepository.existsByOwnerId((String) auth.getPrincipal().getAttributes().get("email"))) {
                return "redirect:/"; // TODO: Redirect to owned seller detail page, with error message
            } else {
                entity.setOwnerId((String) auth.getPrincipal().getAttributes().get("email"));
            }
        }

        entity.setImage(imageEntity);
        sellerRepository.saveAndFlush(entity);

        return "redirect:/sellers"; // TODO: Redirect to detail view.
    }

}
