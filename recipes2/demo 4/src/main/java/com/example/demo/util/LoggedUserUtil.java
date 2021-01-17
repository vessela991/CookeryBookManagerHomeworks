package com.example.demo.util;

import org.springframework.stereotype.Component;

import com.example.demo.model.Gender;
import com.example.demo.model.Recipe;
import com.example.demo.model.Role;
import com.example.demo.model.User;

@Component
public class LoggedUserUtil {

    private Boolean isUserNull(User user) {
        return user == null;
    }

    public Boolean isOwnerOrAdmin(User user, Recipe recipe) {
        return !isUserNull(user) && (user.getId().equals(recipe.getUserId()) || isAdmin(user));
    }

    public Boolean isAdmin(User user) {
        return !isUserNull(user) && user.getUserRole().equals(Role.ADMIN);
    }

    public Boolean isMale(User user) {
        return user.getGender().equals(Gender.MALE);
    }

    public Boolean isFemale(User user, Recipe recipe) {
        return user.getId().equals(recipe.getUserId()) || user.getUserRole().equals(Role.ADMIN);
    }

    public Boolean isOther(User user, Recipe recipe) {
        return user.getId().equals(recipe.getUserId()) || user.getUserRole().equals(Role.ADMIN);
    }

}
