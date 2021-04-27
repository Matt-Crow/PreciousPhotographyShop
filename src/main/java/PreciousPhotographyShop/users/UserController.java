package PreciousPhotographyShop.users;

import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author: Daniel V.
 * Contains all the functional HTTP calls.
 */

@RestController
@RequestMapping("/api")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     *  Returns a list of all users in the Database
     **/
    @GetMapping("/users/{token}")
    public List<UserEntity> getUsers(){
        return userService.getAllUsers();
    }

    /**
     * Searches for a single user in the database by id
     * @param email searches for a user by id
     * @return returns the user's information
     */
    @GetMapping("/findUser")
    public UserEntity findUser(@PathVariable("email") String email){
        return userService.getSingleUser(email);
    }

    /**
     * Saves and registers an new user in the database
     * @param user The user to be stored
     */
    @PostMapping
    public void registerNewUser(@RequestBody UserEntity user){
        userService.addNewUser(user);
    }

    /**
     * Updated user given the name or email
     * @param id Main entry for searching
     * @param first_name Doesn't require updating first name
     * @param last_name Doesn't require updating last name
     * @param password Doesn't require updating password
     * @param username Doesn't require updating username
     * @param email Doesn't require updating email
     */
    @PutMapping(path = "{userID}")
    public void updateUser(@PathVariable("userID") String id,
                           @RequestParam(required = false) String first_name,
                           @RequestParam(required = false) String last_name,
                           @RequestParam(required = false) String password,
                           @RequestParam(required = false) String username,
                           @RequestParam(required = false) String email){

        userService.updateUser(id, first_name, last_name, password, email, username);
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
