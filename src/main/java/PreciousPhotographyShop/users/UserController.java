package PreciousPhotographyShop.users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author: Daniel V.
 * Contains all the functional HTTP calls.
 */

@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     *  Returns a list of all users in the Database
     **/
    @GetMapping("/users")
    public List<User> getUsers(){
        return userService.getAllUsers();
    }

    /**
     * Searches for a single user in the database by id
     * @param id searches for a user by id
     * @return returns the user's information
     */
    @GetMapping("/findUser")
    public User findUser(@PathVariable("userID") String id){
        return userService.getSingleUser(id);
    }

    /**
     * Saves and registers an new user in the database
     * @param user The user to be stored
     */
    @PostMapping
    public void registerNewUser(@RequestBody User user){
        userService.addNewUser(user);
    }

    /**
     * Updated user given the name or email
     * @param id Main entry for searching
     * @param name Doesn't require updating name
     * @param email Doesn't require updating email
     */
    @PutMapping(path = "{userID}")
    public void updateUser(@PathVariable("userID") String id, @RequestParam(required = false) String name, @RequestParam(required = false) String email){
        userService.updateUser(id, name, email);
    }

    /**
     * Deletes a user from the database
     * @param id The user to be deleted
     */
    @DeleteMapping(path = "{userId}")
    public void deleteUser(@PathVariable("userId") String id){
        userService.deleteUser(id);
    }
}
