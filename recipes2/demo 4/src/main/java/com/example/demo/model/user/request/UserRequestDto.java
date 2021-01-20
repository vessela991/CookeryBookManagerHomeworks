package com.example.demo.model.user.request;

import lombok.Getter;
import org.springframework.ui.Model;

import javax.servlet.http.HttpSession;

@Getter
public class UserRequestDto {
    private UserLoginModel userLoginModel;
    private UserRegisterModel userRegisterModel;
    private Long userId;
    private Model model;
    private HttpSession session;

    public UserRequestDto(UserLoginModel userLoginModel, HttpSession session) {
        this.userLoginModel = userLoginModel;
        this.session = session;
    }

    public UserRequestDto(UserLoginModel userLoginModel, Model model, HttpSession session) {
        this.userLoginModel = userLoginModel;
        this.model = model;
        this.session = session;
    }

    public UserRequestDto(UserRegisterModel userRegisterModel, Model model, HttpSession session) {
        this.userRegisterModel = userRegisterModel;
        this.model = model;
        this.session = session;
    }

    public UserRequestDto(UserRegisterModel userRegisterModel, Long userId, Model model, HttpSession session) {
        this.userRegisterModel = userRegisterModel;
        this.userId = userId;
        this.model = model;
        this.session = session;
    }

    public UserRequestDto(Long userId) {
        this.userId = userId;
    }
}
