package fmi.springboot.vpopova.recipes.controller;

import fmi.springboot.vpopova.recipes.model.User;
import fmi.springboot.vpopova.recipes.service.impl.UserServiceImpl;
import fmi.springboot.vpopova.recipes.util.ResponseEntityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    UserServiceImpl userService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<User> getUsers() {
        return userService.getUsers();
    }

    @PostMapping
    public ResponseEntity createUser(@RequestBody User user) {
        User savedUser = userService.saveOrUpdate(user);

        return ResponseEntityUtil.UserWithLocationHeader(savedUser.getId(), HttpStatus.CREATED);
    }

    @GetMapping("/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public User getUserById(@PathVariable("userId") Long userId) {
        return userService.getUserById(userId);
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/{userId}")
    public ResponseEntity editUserById(@PathVariable("userId") Long userId, @RequestBody User user, Model model) {
        model.addAttribute("user", user);
        user.setId(userId);
        User updatedUser = userService.saveOrUpdate(user);

        return ResponseEntityUtil.UserWithLocationHeader(updatedUser.getId(), HttpStatus.CREATED);

    }

    @DeleteMapping("/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUserById(@PathVariable("userId") Long userId) {
        userService.deleteUserById(userId);
    }
}
