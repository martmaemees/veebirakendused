package ee.cs.ut.wad2018.viinavaatlus.controllers;

import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

import static org.springframework.http.MediaType.TEXT_PLAIN_VALUE;

@Controller
@RequestMapping("auth")
public class AuthController {

    @GetMapping(path = "login")
    public String loginPage(OAuth2AuthenticationToken authentication) {
        if (authentication != null && authentication.isAuthenticated()) {
            return "redirect:/";
        }
        return "auth/login";
    }

    @GetMapping(path = "test", produces = TEXT_PLAIN_VALUE)
    public String testPath(OAuth2AuthenticationToken authentication) {
        for (Map.Entry<String, Object> entry : authentication.getPrincipal().getAttributes().entrySet()) {
            System.out.println(entry.getKey());
            System.out.println(entry.getValue());
        }

        return "redirect:/";
    }


}
