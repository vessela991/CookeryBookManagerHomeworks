package com.example.demo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.example.demo.model.UserLoginModel;
import com.example.demo.model.UserRegisterModel;
import lombok.Getter;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
@Getter
public class UserValidationException extends RuntimeException {

    private final UserRegisterModel userRegisterModel;
    private final UserLoginModel userLoginModel;
    private String mode = "";

    public UserValidationException(String message, UserRegisterModel userRegisterModel, UserLoginModel userLoginModel) {
        super(message);
        this.userRegisterModel = userRegisterModel;
        this.userLoginModel = userLoginModel;
        if (userLoginModel != null) {
            this.mode = "Login";
        }

        if (userRegisterModel != null) {
            if (userRegisterModel.getId() == null) {
                this.mode = "Register";
            } else {
                this.mode = "Edit";
            }
        }
    }
}
