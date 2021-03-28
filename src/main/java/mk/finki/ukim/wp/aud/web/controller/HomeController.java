package mk.finki.ukim.wp.aud.web.controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
//@RequestMapping("/")
//mu ovozmozuvame na kontrolerot da pristapuva do stranata / i /home i da go
//prikaze master template-ot
@RequestMapping(value = {"/", "/home"})
public class HomeController {

    //@GetMapping("home")
    @GetMapping
    public String getHomePage(Model model) {
        model.addAttribute("bodyContent", "home");
        return "master-template";
    }

    @GetMapping("access_denied")
    public String accessDenied(Model model) {
        model.addAttribute("bodyContent", "access_denied");
        return "master-template";
    }
}
