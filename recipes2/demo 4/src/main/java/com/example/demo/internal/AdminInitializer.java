package com.example.demo.internal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import com.example.demo.UserRepository;
import com.example.demo.model.Gender;
import com.example.demo.model.Role;
import com.example.demo.model.User;

@Component
public class AdminInitializer implements ApplicationRunner {
    @Autowired
    private UserRepository userRepository;

    @Override
    public void run(ApplicationArguments args) {
        if (userRepository.findByUsername("admin") == null) {
            User user = new User();
            user.setUsername("admin");
            user.setPassword("admin1234!");
            user.setUserInfo("initial admin account");
            user.setGender(Gender.MALE);
            user.setUserRole(Role.ADMIN);
            userRepository.save(user);
        }
    }
}
