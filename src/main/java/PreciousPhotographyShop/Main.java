package PreciousPhotographyShop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Main {    
    public static void main(String[] args) {
        todo check if application.properties exists
        SpringApplication.run(Main.class, args);
        System.out.println("Program started");
    }
}
