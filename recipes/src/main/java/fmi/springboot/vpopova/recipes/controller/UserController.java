package fmi.springboot.vpopova.recipes.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import fmi.springboot.vpopova.recipes.model.User;
import fmi.springboot.vpopova.recipes.service.impl.UserServiceImpl;
import fmi.springboot.vpopova.recipes.util.ResponseEntityUtil;

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

    @GetMapping("/:userId")
    @ResponseStatus(HttpStatus.OK)
    public User getUserById(@PathVariable("userId") String userId) {
        return userService.getUserById(userId);
    }

    @PutMapping("/:userId")
    public ResponseEntity editUserById(@PathVariable("userId") String userId, @RequestBody User user) {
        user.setId(userId);
        User updatedUser = userService.saveOrUpdate(user);

        return ResponseEntityUtil.UserWithLocationHeader(updatedUser.getId(), HttpStatus.CREATED);

    }

    @DeleteMapping("/:userId")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUserById(@PathVariable("userId") String userId) {
        userService.deleteUserById(userId);
    }
}
