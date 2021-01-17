package com.example.demo.model;

import java.io.BufferedOutputStream;
import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    @NonNull
    @Column(unique=true)
    @Size(max = 15)
    private String username;
    @NonNull
    @Size(min = 8)
    private String password;
    @NonNull
    private Gender gender;
    @NonNull
    private Role userRole = Role.USER;
    @NonNull
    @Size(max = 512)
    private String userInfo;
    @NonNull
    private AccountStatus accountStatus = AccountStatus.ACTIVE;
    private Date registrationTime = new Date();
    private Date lastModified = new Date();
    @Column(length = Integer.MAX_VALUE)
    private String picture;

    public static User fromUserRegisterModel(UserRegisterModel userRegisterModel) {
        User user = new User();
        user.setUsername(userRegisterModel.getUsername());
        user.setPassword(userRegisterModel.getPassword());
        user.setUserInfo(userRegisterModel.getUserInfo());
        user.setGender(userRegisterModel.getGender());
        return user;
    }

}
