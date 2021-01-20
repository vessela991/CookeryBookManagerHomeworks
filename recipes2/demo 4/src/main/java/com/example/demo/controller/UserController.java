package com.example.demo.controller;

import javax.servlet.http.HttpSession;

import com.example.demo.internal.Constants;
import com.example.demo.model.user.request.UserRequestDto;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.model.user.request.UserLoginModel;
import com.example.demo.model.user.request.UserRegisterModel;

@Controller()
public class UserController {
    @Autowired
    private UserService userService;

    // Login
    @GetMapping("/login")
    public String getLogin(@ModelAttribute("user") UserLoginModel requestModel, Model model, HttpSession session) {
        return userService.getLogin(new UserRequestDto(requestModel, model, session));
    }

    @PostMapping("/login")
    public String login(@ModelAttribute("user") UserLoginModel requestModel, Model model, HttpSession session) {
        return userService.login(new UserRequestDto(requestModel, model, session));
    }

    // Register
    @GetMapping("/register")
    public String getRegister(@ModelAttribute("user") UserRegisterModel requestModel,
            @RequestParam(value="id", required=false) Long userId,
            Model model,
            HttpSession session) {
        return userService.getRegister(new UserRequestDto(requestModel, userId, model, session));
    }

    @PostMapping("/register")
    public String register(@ModelAttribute("user") UserRegisterModel requestModel,
                           Model model, HttpSession session) {
        return userService.register(new UserRequestDto(requestModel, model, session));
    }

    // Users
    @GetMapping("/users")
    public String getUsers(@ModelAttribute("user") UserRegisterModel requestModel, Model model, HttpSession session) {
        return userService.getUsers(new UserRequestDto(requestModel, model, session));
    }

    @PostMapping(value = "/users", params = ("userDeleteId"))
    public String deleteUser(@RequestParam("userDeleteId") Long userId) {
        return userService.deleteUser(new UserRequestDto(userId));
    }

    @PostMapping(value = "/users", params = ("userEditId"))
    public String editUser(@RequestParam("userEditId") Long userId) {
        return userService.editUser(new UserRequestDto(userId));
    }

    // Logout
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return Constants.REDIRECT;
    }
}
