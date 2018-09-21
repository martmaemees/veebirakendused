package ee.cs.ut.wad2018.viinavaatlus.controllers;

import ee.cs.ut.wad2018.viinavaatlus.repositories.SellerRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("sellers")
public class SellerController {

    private final SellerRepository sellerRepository;

    SellerController(SellerRepository sellerRepository) {
        this.sellerRepository = sellerRepository;
    }

    @GetMapping()
    public String showAllSellers(Model model) {
        model.addAttribute("sellers", sellerRepository.findAll());
        return "sellers/all";
    }

}
