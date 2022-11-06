package com.example.kata;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.web.cors.CorsConfiguration;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // Enable tomcat basic authentication
        // Disable csrf for all requests
        // Allow CORS
        http.cors().configurationSource(request -> {
                    CorsConfiguration cors = new CorsConfiguration();
                    cors.setAllowedOrigins(Arrays.asList("http://localhost:4200"));
                    cors.setAllowedMethods(Arrays.asList("GET", "PUT"));
                    cors.setAllowedHeaders(Arrays.asList("*"));
                    return cors;
                }).and()
                .csrf().disable().authorizeRequests().anyRequest().authenticated().and().httpBasic();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser("user")
                .password("{noop}123") // {noop} is added to prevent any processing on the password
                .roles("USER");
    }
}
