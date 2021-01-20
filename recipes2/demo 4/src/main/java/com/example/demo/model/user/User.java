package com.example.demo.model.user;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import com.example.demo.model.user.request.UserRegisterModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class User {
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

    public static User fromUserRegisterModel(UserRegisterModel userRegisterModel, String picture) {
        User user = new User();
        user.setId(userRegisterModel.getId());
        user.setUsername(userRegisterModel.getUsername());
        user.setPassword(userRegisterModel.getPassword());
        user.setUserInfo(userRegisterModel.getUserInfo());
        user.setGender(userRegisterModel.getGender());
        user.setUserRole(userRegisterModel.getUserRole());
        user.setPicture(picture);
        return user;
    }
}
