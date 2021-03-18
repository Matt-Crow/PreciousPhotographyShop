package PreciousPhotographyShop;

import PreciousPhotographyShop.databaseInterface.RealDatabaseInterface;
import PreciousPhotographyShop.databaseInterface.DatabaseInterface;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Main {
    /*
    Look at this: any part of the program can access the database interface
    through Main.DATABASE, without needing to know exactly which implementation
    is used, so we can easily swap between implementations by simply changing
    this file.
    */
    public static final DatabaseInterface DATABASE = new RealDatabaseInterface();
    
    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
        System.out.println("Program started");        
    }
}
