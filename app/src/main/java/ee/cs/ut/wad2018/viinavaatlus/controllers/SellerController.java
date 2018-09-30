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

@Slf4j
@Controller
@RequestMapping("sellers")
public class SellerController {

    private final SellerRepository sellerRepository;
    private final SellerImageRepository sellerImageRepository;

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

    @GetMapping(path = "{id}")
    public String getDetails(@PathVariable Long id, Model model) {
        Optional<Seller> entity = sellerRepository.findById(id);
        if (!entity.isPresent()) {
            return "redirect:/"; //TODO: Show error message here.
        }
        model.addAttribute("seller", new SellerDTO(entity.get()));
        return "sellers/details";
    }

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
        sellerRepository.save(entity);

        return "redirect:/sellers"; // TODO: Redirect to detail view.
    }



}
