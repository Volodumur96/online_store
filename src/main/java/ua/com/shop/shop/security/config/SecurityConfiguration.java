package ua.com.shop.shop.security.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.servlet.Filter;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration  {

    @Value("${secured.url.pattern}")
    private String securedUrlPattern;

    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder(8);
    }

    @Bean
    public FilterRegistrationBean authorizationFilterRegistration() {

        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter((Filter) jwtAuthenticationFilter());

        registration.addUrlPatterns(securedUrlPattern);
        registration.addInitParameter("paramName", "paramValue");
        registration.setName("jwtFilter");
        registration.setOrder(0);
        return registration;
    }

    @Bean(name = "jwtFilter")
    public Filter jwtAuthenticationFilter() {
        return (new TokenAuthentificationFilter());
    }

}
