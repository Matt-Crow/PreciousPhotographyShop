package PreciousPhotographyShop.users;

import PreciousPhotographyShop.users.User;
import java.util.Arrays;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    public List<User> getUser(){
        return Arrays.asList(new User("Daniel", "dany.villavicencio30@gmail.com"));
    }
}
