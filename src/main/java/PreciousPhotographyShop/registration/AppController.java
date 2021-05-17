package PreciousPhotographyShop.registration;

import PreciousPhotographyShop.users.UserEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AppController {

    @GetMapping("/register")
    public String showSignUpForm(Model model){
        model.addAttribute("user", new UserEntity());

        return "create_account";
    }
}
