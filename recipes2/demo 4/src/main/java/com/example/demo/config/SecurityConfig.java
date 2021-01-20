package com.example.demo.config;

import com.example.demo.model.user.Role;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.httpBasic().disable();
        http.formLogin().disable();
        http.csrf().disable()
                .authorizeRequests().antMatchers("/").permitAll()
                .and().authorizeRequests().antMatchers("/login").permitAll()
                .and().authorizeRequests().antMatchers("/register/**").permitAll()
                .and().authorizeRequests().antMatchers("/users/**").permitAll()
                .and().authorizeRequests().antMatchers("/style.css/**").permitAll()
                .and().authorizeRequests().antMatchers( "/recipe**").permitAll()
                .and().authorizeRequests().antMatchers( "/recipes/**").permitAll()
                .and().authorizeRequests().antMatchers("/recipe-form").permitAll()
                .and().authorizeRequests().antMatchers("/recipe-form/**").permitAll()
                .anyRequest().authenticated()
                .and().sessionManagement()
                .invalidSessionUrl("/").sessionCreationPolicy(SessionCreationPolicy.ALWAYS);
    }

}
