package PreciousPhotographyShop.registration;

import java.util.Objects;

public class RegistrationRequest {

    private final String firstName;
    private final String lastName;
    private final String username;
    private final String password;
    private final String email;

    public RegistrationRequest(String firstName, String lastName, String username, String password, String email){
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
        this.email = email;
    }

    public String getFirstName() { return firstName; }

    public String getLastName() { return lastName; }

    public String getUsername() { return username; }

    public String getEmail() {
        return email;
    }

    public String getPassword() { return password; }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName, username, password, email);
    }

    @Override
    public String toString() {
        return "RegistrationRequest{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}


