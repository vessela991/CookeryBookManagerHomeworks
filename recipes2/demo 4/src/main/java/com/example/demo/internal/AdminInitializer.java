package com.example.demo.internal;

import com.example.demo.model.ValidationResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Component;

import com.example.demo.respository.UserRepository;
import com.example.demo.model.user.Gender;
import com.example.demo.model.user.Role;
import com.example.demo.model.user.User;
import org.springframework.web.multipart.MultipartFile;

import javax.sql.rowset.serial.SerialBlob;
import java.io.File;
import java.io.FileInputStream;
import java.sql.Blob;
import java.util.Base64;

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
            try {
                File file = new File("src/main/resources/default-avatar.jpg");
                FileInputStream fileInputStream = new FileInputStream(file);
                MultipartFile multipartFile = new MockMultipartFile(file.getName(), file.getName(), MediaType.TEXT_HTML_VALUE, fileInputStream);
                Blob pictureBlob = new SerialBlob(multipartFile.getBytes());
                String picture = Base64.getEncoder().encodeToString(pictureBlob.getBytes(1, (int) pictureBlob.length()));
                user.setPicture(picture);
            }
            catch (Exception e) {

            }

            userRepository.save(user);
        }
    }
}
