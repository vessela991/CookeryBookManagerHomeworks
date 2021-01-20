package com.example.demo.service;

import com.example.demo.internal.Constants;
import com.example.demo.internal.Validator;
import com.example.demo.model.ValidationResult;
import com.example.demo.model.user.User;
import com.example.demo.model.user.request.UserRequestDto;
import com.example.demo.model.user.request.UserRegisterModel;
import com.example.demo.respository.UserRepository;
import com.example.demo.util.LoggedUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.sql.rowset.serial.SerialBlob;
import java.io.File;
import java.io.FileInputStream;
import java.sql.Blob;
import java.util.Base64;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private Validator validator;
    @Autowired
    private LoggedUser loggedUser;

    // login
    public String getLogin(UserRequestDto requestDto) {
        requestDto.getModel().addAttribute(Constants.ATTR_TITLE, "Login");
        requestDto.getModel().addAttribute(Constants.ATTR_LOGGEDUSERUTIL, loggedUser);
        return Constants.TMPL_LOGIN;
    }

    public String login(UserRequestDto requestDto) {
        User user = userRepository.findByUsername(requestDto.getUserLoginModel().getUsername());
        ValidationResult validation = validator.validateUserLogin(requestDto.getUserLoginModel(), user);

        if (!validation.isSuccess()){
            requestDto.getModel().addAttribute(Constants.ATTR_TITLE, "Login");
            requestDto.getModel().addAttribute(Constants.ATTR_LOGGEDUSERUTIL, loggedUser);
            requestDto.getModel().addAttribute(Constants.ATTR_ERROR, validation.getResult());
            return Constants.TMPL_LOGIN;
        }

        requestDto.getSession().setAttribute(Constants.ATTR_LOGGEDUSER, user);
        return Constants.REDIRECT;
    }

    // register
    public String getRegister(UserRequestDto requestDto) {
        requestDto.getModel().addAttribute(Constants.ATTR_TITLE, "Register");
        requestDto.getModel().addAttribute(Constants.ATTR_LOGGEDUSERUTIL, loggedUser);
        requestDto.getModel().addAttribute(Constants.ATTR_LOGGEDUSER, requestDto.getSession().getAttribute(Constants.ATTR_LOGGEDUSER));

        // edit
        if (requestDto.getUserId() != null) {
            if (requestDto.getSession().getAttribute(Constants.ATTR_LOGGEDUSER) == null) {
                return Constants.REDIRECT + Constants.ROUTE_LOGIN;
            }
            requestDto.getModel().addAttribute(Constants.ATTR_TITLE, "Edit User");
            User user = userRepository.findById(requestDto.getUserId()).orElse(null);
            if (user == null) {
                requestDto.getModel().addAttribute(Constants.ATTR_ERROR, "User not found with id: " + user.getId());
                return Constants.REDIRECT + Constants.ROUTE_USERS;
            }
            requestDto.getModel().addAttribute(Constants.ATTR_USER, user);
        }

        return Constants.TMPL_REGISTER;
    }

    public String register(UserRequestDto requestDto) {
        requestDto.getModel().addAttribute(Constants.ATTR_LOGGEDUSERUTIL, this.loggedUser);
        requestDto.getModel().addAttribute(Constants.ATTR_LOGGEDUSER, requestDto.getSession().getAttribute(Constants.ATTR_LOGGEDUSER));

        boolean isEdit = requestDto.getUserRegisterModel().getId() != null;

        if (isEdit) {
            if (requestDto.getSession().getAttribute(Constants.ATTR_LOGGEDUSER) == null) {
                return Constants.REDIRECT + Constants.ROUTE_LOGIN;
            }
            requestDto.getModel().addAttribute(Constants.ATTR_TITLE, "Edit User");
        }
        else {
            requestDto.getModel().addAttribute(Constants.ATTR_TITLE, "Register");
        }

        ValidationResult<String> validation = validator.validateUserRegistration(requestDto.getUserRegisterModel(), isEdit);
        if (!validation.isSuccess()) {
            requestDto.getModel().addAttribute(Constants.ATTR_USER, requestDto.getUserRegisterModel());
            requestDto.getModel().addAttribute(Constants.ATTR_ERROR, validation.getResult());
            return Constants.TMPL_REGISTER;
        }

        validation = handlePicture(requestDto.getUserRegisterModel(), isEdit);
        if (!validation.isSuccess()) {
            requestDto.getModel().addAttribute(Constants.ATTR_USER, requestDto.getUserRegisterModel());
            requestDto.getModel().addAttribute(Constants.ATTR_ERROR, validation.getResult());
            return Constants.TMPL_REGISTER;
        }

        User user = User.fromUserRegisterModel(requestDto.getUserRegisterModel(), validation.getResult());
        userRepository.save(user);
        return isEdit ? Constants.REDIRECT + Constants.ROUTE_USERS : Constants.REDIRECT;
    }

    // users
    public String getUsers(UserRequestDto requestDto) {
        requestDto.getModel().addAttribute(Constants.ATTR_LOGGEDUSERUTIL, loggedUser);

        if (requestDto.getSession().getAttribute(Constants.ATTR_LOGGEDUSER) == null) {
            return Constants.REDIRECT;
        }
        requestDto.getModel().addAttribute(Constants.ATTR_LOGGEDUSER,
                requestDto.getSession().getAttribute(Constants.ATTR_LOGGEDUSER));
        requestDto.getModel().addAttribute(Constants.ATTR_USERS, userRepository.findAll());
        return Constants.TMPL_USERS;
    }

    public String deleteUser(UserRequestDto requestDto) {
        if (requestDto.getSession().getAttribute(Constants.ATTR_LOGGEDUSER) == null) {
            return Constants.REDIRECT + Constants.ROUTE_LOGIN;
        }
        userRepository.deleteById(requestDto.getUserId());
        return Constants.REDIRECT + Constants.ROUTE_USERS;
    }

    public String editUser(UserRequestDto requestDto) {
        if (requestDto.getSession().getAttribute(Constants.ATTR_LOGGEDUSER) == null) {
            return Constants.REDIRECT + Constants.ROUTE_LOGIN;
        }
        return Constants.REDIRECT + Constants.ROUTE_REGISTER + String.format("?id=%d", requestDto.getUserId());
    }

    // helper methods
    private ValidationResult<String> handlePicture(UserRegisterModel userRegisterModel, Boolean isEdit) {
        try {
            // if it is edit don't change the picture
            if (isEdit) {
                User user = userRepository.findById(userRegisterModel.getId()).orElse(null);
                if (user == null) {
                    return new ValidationResult<>(false, "User not found with id: " + userRegisterModel.getId());
                }

                return new ValidationResult<>(true, user.getPicture());
            }


            if (userRegisterModel.getPicture() == null || userRegisterModel.getPicture().getResource().getFilename().equals("")) {
                File picture = new File("src/main/resources/default-avatar.jpg");
                FileInputStream fileInputStream = new FileInputStream(picture);
                MultipartFile multipartFile = new MockMultipartFile(picture.getName(), picture.getName(),
                        MediaType.TEXT_HTML_VALUE, fileInputStream);
                userRegisterModel.setPicture(multipartFile);
            }

            Blob pictureBlob = new SerialBlob(userRegisterModel.getPicture().getBytes());
            String picture = Base64.getEncoder().encodeToString(pictureBlob.getBytes(1, (int) pictureBlob.length()));
            return new ValidationResult<>(true, picture);
        }
        catch (Exception exception) {
            return new ValidationResult<>(false, "System error, please insert another picture");
        }
    }
}
