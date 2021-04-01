package PreciousPhotographyShop.users;

import java.util.Arrays;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    public List<UserEntity> getUser(){
        return Arrays.asList(new UserEntity("Daniel", "dany.villavicencio30@gmail.com"));
    }
}
