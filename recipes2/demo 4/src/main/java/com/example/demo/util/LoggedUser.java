package com.example.demo.util;

import com.example.demo.model.recipe.Recipe;
import org.springframework.stereotype.Component;

import com.example.demo.model.user.Gender;
//import com.example.demo.model.Recipe;
import com.example.demo.model.user.Role;
import com.example.demo.model.user.User;

@Component
public class LoggedUser {

    private Boolean isUserNull(User user) {
        return user == null;
    }

    public Boolean isAdmin(User user) {
        return !isUserNull(user) && isUserAdmin(user);
    }

    private Boolean isUserAdmin(User user) {
        return user.getUserRole().equals(Role.ADMIN);
    }

    public Boolean isOwnerOrAdmin(User user, Recipe recipe) {
        return !isUserNull(user) && (user.getId().equals(recipe.getUserId()) || isUserAdmin(user));
    }


    //public Boolean isMale(User user) {
//        return user.getGender().equals(Gender.MALE);
//    }

//    public Boolean isFemale(User user, Recipe recipe) {
//        return user.getId().equals(recipe.getUserId()) || user.getUserRole().equals(Role.ADMIN);
//    }
//
//    public Boolean isOther(User user, Recipe recipe) {
//        return user.getId().equals(recipe.getUserId()) || user.getUserRole().equals(Role.ADMIN);
//    }

}
