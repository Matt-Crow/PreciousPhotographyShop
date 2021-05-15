package PreciousPhotographyShop;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Scanner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.io.ClassPathResource;

@SpringBootApplication
public class Main {    
    public static void main(String[] args) {
        if(!propertiesExist()){
            printHelpMessage();
            pause();
        }
        SpringApplication.run(Main.class, args);
        System.out.println("Program started");
    }
    
    private static boolean propertiesExist(){
        return new ClassPathResource("application.properties").exists();
    }
    
    private static void printHelpMessage(){
        System.out.println("I couldn't find the file application.properties");
        System.out.println("Can you create a file named application.properties in the same folder as this JAR file, and fill it out for me?");
        try (
            InputStream in = Main.class.getResourceAsStream("/sampleApp.properties");
            InputStreamReader read = new InputStreamReader(in);
            BufferedReader buff = new BufferedReader(read);
        ){
            System.out.println("Here's the template: \n\n\n");
            buff.lines().forEach(System.out::println);
        } catch (IOException ex) {
            System.out.println("You can find a template under /src/main/resources that you can copy and fill out");
        }
    }
    
    private static void pause(){
        System.out.print("\n\n\nPress any key to continue...");
        try (Scanner in = new Scanner(System.in)) {
            in.nextLine();
        }
    }
}
