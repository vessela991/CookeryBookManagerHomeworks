package fmi.springboot.vpopova.recipes.service;

import fmi.springboot.vpopova.recipes.model.User;

import java.util.List;

public interface UserService {
    User saveOrUpdate(User user);

    User getUserById(String id);

    List<User> getUsers();

    void deleteUserById(String id);

    User findUserByUsername(String username);
}
