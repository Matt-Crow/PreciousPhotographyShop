package PreciousPhotographyShop.registration;

import lombok.*;

@Getter
@EqualsAndHashCode
@ToString
public class RegistrationRequest {

    private final String name;
    private final String password;
    private final String email;

    public RegistrationRequest(String name, String password, String email){
        this.name = name;
        this.password = password;
        this.email = email;
    }
}


