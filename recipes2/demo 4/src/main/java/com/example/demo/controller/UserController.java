package com.example.demo.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.Base64;

import javax.servlet.http.HttpSession;
import javax.sql.rowset.serial.SerialBlob;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.UserRepository;
import com.example.demo.exception.NotFoundException;
import com.example.demo.internal.Constants;
import com.example.demo.model.User;
import com.example.demo.model.UserLoginModel;
import com.example.demo.model.UserRegisterModel;
import com.example.demo.util.LoggedUserUtil;
import com.example.demo.util.Validator;

@Controller
public class UserController {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private Validator validator;

    @Autowired
    private LoggedUserUtil loggedUserUtil;

    // Register
    @GetMapping("/register")
    public ModelAndView getUserForm(@ModelAttribute("user") UserRegisterModel requestModel,
            @RequestParam(value="userId", required=false) Long userId,
            HttpSession session) {
        ModelAndView mv = new ModelAndView(Constants.TMPL_REGISTER);
        mv.addObject(Constants.ATTR_TITTLE, "Register");

        if (userId != null) {
            mv.addObject(Constants.ATTR_USER, userRepository.findById(userId));
            mv.addObject(Constants.ATTR_LOGGEDUSER, session.getAttribute(Constants.ATTR_USER));
            mv.addObject(Constants.ATTR_TITTLE, "Edit");
        }

        return mv;
    }

    @PostMapping("/register")
    public String postUserForm(@ModelAttribute("user") UserRegisterModel userRegisterModel,
            Model model,
            HttpSession session)
            throws IOException, SQLException {
        validator.validateUserCredentials(userRegisterModel);
        User user = User.fromUserRegisterModel(userRegisterModel);

        // if there is a user id, and logged user is admin, this is edit
        boolean isEdit = false;
        if (userRegisterModel.getId() != null) {
            User loggedUser = (User) session.getAttribute(Constants.ATTR_USER);
            if (loggedUserUtil.isAdmin(loggedUser)) {
                user.setUserRole(userRegisterModel.getUserRole());
                isEdit = true;
            }
       }
        user.setPicture(handlePicture(userRegisterModel, isEdit));
        userRepository.save(user);
        return isEdit ? Constants.TMPL_USERS : Constants.REDIRECT;
    }

    // Login
    @GetMapping("/login")
    public String getLogin(@ModelAttribute("user") User user) {
        return Constants.TMPL_LOGIN;
    }

    @PostMapping("/login")
    public String login(@ModelAttribute("user") UserLoginModel requestModel, HttpSession session) {
        User user = userRepository.findByUsername(requestModel.getUsername());
        validator.validateUserLogin(requestModel, user);
        session.setAttribute(Constants.ATTR_USER, user);
        return Constants.REDIRECT;
    }

    // Users
    @GetMapping("/users")
    public String getAllUsers(@ModelAttribute("user") User requestModel, Model model, HttpSession session) {
        model.addAttribute(Constants.ATTR_LOGGEDUSER, session.getAttribute(Constants.ATTR_USER));
        model.addAttribute(Constants.ATTR_LOGGEDUSERUTIL, loggedUserUtil);
        model.addAttribute(Constants.ATTR_USERS, userRepository.findAll());
        return Constants.TMPL_USERS;
    }

    @PostMapping(value = "/users", params = ("userDeleteId"))
    public String deleteUserById(@RequestParam("userDeleteId") Long id) {
        userRepository.deleteById(id);
        return Constants.REDIRECT + Constants.TMPL_USERS;
    }

    @PostMapping(value = "/users", params = ("userEditId"))
    public String editUserById(@RequestParam("userEditId") Long id) {
        return Constants.REDIRECT + Constants.TMPL_REGISTER + String.format("?userId=%d", id);
    }

    // Logout
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return Constants.REDIRECT;
    }

    private String handlePicture(UserRegisterModel userRegisterModel, Boolean isEdit) throws IOException, SQLException {
        // if it is edit don't change the picture
        if (isEdit) {
            User user = userRepository.findById(userRegisterModel.getId()).orElseThrow(() -> new NotFoundException(
                    "User not found with id: " + userRegisterModel.getId()));
            return user.getPicture();
        }

        // if user doesn't set a picture set default one - register
        if (userRegisterModel.getPicture().getResource().getFilename().isBlank()) {
            File picture = new File("src/main/resources/default-avatar.jpg");
            FileInputStream fileInputStream = new FileInputStream(picture);
            MultipartFile multipartFile = new MockMultipartFile(picture.getName(), picture.getName(),
                    MediaType.TEXT_HTML_VALUE, fileInputStream);
            userRegisterModel.setPicture(multipartFile);
        }

        Blob pictureBlob = new SerialBlob(userRegisterModel.getPicture().getBytes());
        return Base64.getEncoder().encodeToString(pictureBlob.getBytes(1, (int) pictureBlob.length()));
    }
}
