package PreciousPhotographyShop.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;

@Configuration
public class MvcConfig {
    public void addViewControllers(ViewControllerRegistry registry){
        registry.addViewController("").setViewName("home");
        registry.addViewController("/createAccount").setViewName("create_account");
    }
}
