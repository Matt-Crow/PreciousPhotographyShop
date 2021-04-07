package PreciousPhotographyShop.registration;

import lombok.*;
import lombok.AllArgsConstructor;

@Getter
@EqualsAndHashCode
@AllArgsConstructor
@ToString
public class RegistrationRequest {

    private final String name;
    private final String password;
    private final String email;


}


