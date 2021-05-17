package PreciousPhotographyShop.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
<<<<<<< HEAD

@Configuration
public class MvcConfig {
    public void addViewControllers(ViewControllerRegistry registry){
        registry.addViewController("").setViewName("home");
        registry.addViewController("/createAccount").setViewName("create_account");
    }
}
=======
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfig implements WebMvcConfigurer {
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("").setViewName("index-2");
        registry.addViewController("/").setViewName("index-2");
        registry.addViewController("/login").setViewName("login");
    }
}
>>>>>>> b66707e9cf261511338991d71957f6470f483c88
