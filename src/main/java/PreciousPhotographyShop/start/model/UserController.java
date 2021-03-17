package PreciousPhotographyShop.start.model;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/v1/user")
public class UserController {

    @GetMapping
    public List<User> getUser(){
        return List.of(
                new User("Daniel", "dany.villavicencio30@gmail.com", "1010")
        );
    }

}
