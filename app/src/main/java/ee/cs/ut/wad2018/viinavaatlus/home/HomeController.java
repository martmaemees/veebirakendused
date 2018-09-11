package ee.cs.ut.wad2018.viinavaatlus.home;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HomeController {

    @RequestMapping(path = "/home", method = RequestMethod.GET)
    public String getHomePage() {
        return "home/index";
    }

    // SAme as @RequestMapping(method = RequestMethod.GET)
    @GetMapping(path = "/home-dynamic")
    public String getHome2Page(@RequestParam(value = "name", required = false, defaultValue = "World") String name,
                               Model model) {
        model.addAttribute("name", name);
        return "home/index_dynamic";
    }

}
