package com.example.demo.model;

import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserRegisterModel {
    private Long id;
    private String username;
    private String password;
    private Gender gender;
    private Role userRole = Role.USER;
    private String userInfo;
    private MultipartFile picture;
}
