package PreciousPhotographyShop.users;

import PreciousPhotographyShop.users.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("api/v1/")
public class UserController {

    @Autowired
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public void registerNewUser(@RequestBody User user){
        userService.addNewUser(user);
    }

    @PutMapping(path = "{userID}")
    public void updateUser(
        @PathVariable("userID") String id,
        @RequestParam(required = false) String name,
        @RequestParam(required = false) String email){
        userService.updateUser(id, name, email);
    }

    @DeleteMapping(path = "{userId}")
    public void deleteUser(@PathVariable("userId") String id){
        userService.deleteUser(id);
    }
}
