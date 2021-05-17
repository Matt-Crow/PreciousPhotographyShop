package PreciousPhotographyShop.security.config;


import PreciousPhotographyShop.security.LoginLogoutHandler;
import PreciousPhotographyShop.users.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    private final LoginLogoutHandler loginLogoutHandler;
    private final UserService userService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public WebSecurityConfig(LoginLogoutHandler loginLogoutHandler, UserService userService, BCryptPasswordEncoder bCryptPasswordEncoder){
        this.loginLogoutHandler = loginLogoutHandler;
        this.userService = userService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // todo: probably should secure some of the API so nobody can delete users...
        http.csrf().disable().
            authorizeRequests().
                //antMatchers("/", "/index", "/search", "/login", "/allPhotos", "/api/registration/**", "/resources/**").permitAll().
                //anyRequest().authenticated().
                antMatchers("/newPhoto", "/addToCart", "/reviews/newPhotoReview", "/log/personal").authenticated().
                anyRequest().permitAll().
            and().
                formLogin().permitAll().successHandler(loginLogoutHandler).
            and().
                logout().permitAll().addLogoutHandler(loginLogoutHandler);
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(daoAuthenticationProvider());
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider(){
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(bCryptPasswordEncoder);
        provider.setUserDetailsService(userService);
        return provider;
    }
}
