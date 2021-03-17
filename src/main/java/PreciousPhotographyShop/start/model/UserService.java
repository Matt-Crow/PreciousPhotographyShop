package PreciousPhotographyShop.start.model;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    public List<User> getUser(){
        return List.of(
                new User("Daniel", "dany.villavicencio30@gmail.com", "1010")
        );
    }
}
