package com.example.demo.config;

import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.httpBasic().disable();
        http.formLogin()
                .loginPage("/login.html")
                .and()
                .logout()
                .logoutSuccessUrl("/");
        http.csrf().disable()
                .authorizeRequests().antMatchers("/register").permitAll()
                .and().authorizeRequests().antMatchers("/login").permitAll()
                .and().authorizeRequests().antMatchers(HttpMethod.GET, "/recipes/**").permitAll()
                .and().authorizeRequests().antMatchers(HttpMethod.GET,"/recipe-form").authenticated()
                .and().authorizeRequests().antMatchers("/users").hasRole("ADMIN")
                .anyRequest().authenticated()
                .and().sessionManagement()
                .invalidSessionUrl("/").sessionCreationPolicy(SessionCreationPolicy.ALWAYS)
        ;
    }

}
