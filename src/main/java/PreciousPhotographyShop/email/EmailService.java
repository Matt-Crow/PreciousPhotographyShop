package PreciousPhotographyShop.email;

import org.springframework.stereotype.Service;

@Service

public class EmailService implements EmailSender{

    private Mail javaMailSender;
    @Override
    public void send(String to, String email) {

    }
}
