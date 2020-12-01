package fmi.springboot.vpopova.recipes.service;

import java.util.List;

import fmi.springboot.vpopova.recipes.model.User;

public interface UserService {
    User saveOrUpdate(User user);

    User getUserById(String id);

    List<User> getUsers();

    void deleteUserById(String id);
}
