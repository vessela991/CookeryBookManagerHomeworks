package com.example.demo.util;

import org.passay.CharacterRule;
import org.passay.EnglishCharacterData;
import org.passay.LengthRule;
import org.passay.PasswordData;
import org.passay.PasswordValidator;
import org.passay.RuleResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.demo.UserRepository;
import com.example.demo.exception.RecipeValidationException;
import com.example.demo.exception.UserValidationException;
import com.example.demo.internal.Constants;
import com.example.demo.model.RecipeCreateModel;
import com.example.demo.model.User;
import com.example.demo.model.UserLoginModel;
import com.example.demo.model.UserRegisterModel;

@Component
public class Validator {
    private static Integer USER_INFO_MAX_LENGTH = 512;
    private static Integer USERNAME_MAX_LENGTH = 15;

    @Autowired
    private UserRepository userRepository;

    PasswordValidator validator = new PasswordValidator(
            new LengthRule(8, 42),
            new CharacterRule(EnglishCharacterData.Digit, 1),
            new CharacterRule(EnglishCharacterData.Special, 1)
    );

    public void validateUserCredentials(UserRegisterModel user) {
        RuleResult validation = validator.validate(new PasswordData(user.getPassword()));

        if (userRepository.findByUsername(user.getUsername()) != null) {
            throw new UserValidationException("Username is already taken!", user, null);
        }
        if (user.getUsername().length() > USERNAME_MAX_LENGTH) {
            throw new UserValidationException("Username is more than 15 characters!", user, null);
        }

        if (user.getUserInfo().length() > USER_INFO_MAX_LENGTH) {
            throw new UserValidationException("Information is more than 512 characters!", user, null);
        }

        if (!validation.isValid()) {
            throw new UserValidationException("Password validation failed. Length must be over 8 characters, must contain"
                    + " at least one digit and at least one special character", user, null);
        }
    }

    public void validateUserLogin(UserLoginModel requestModel, User user) {
        String failedLoginError = "Failed Login";

        if(user == null) {
            throw new UserValidationException(failedLoginError, null, requestModel);
        }

        if(user.getPassword() == null) {
            throw new UserValidationException(failedLoginError, null, requestModel);
        }

        if (!(user.getUsername().equals(requestModel.getUsername()) && user.getPassword().equals(requestModel.getPassword()))) {
            throw new UserValidationException(failedLoginError, null, requestModel);
        }
    }

    public void validateRecipe(RecipeCreateModel recipe, Boolean validatePicture) {
        if (recipe.getName().length() > 80 || recipe.getName().length() < 4) {
            throw new RecipeValidationException("Recipe name must be between 4 and 80 characters!", recipe);
        }

        if (recipe.getTimeToCook() <= 0) {
            throw new RecipeValidationException("You must add time to cook for this recipe!", recipe);
        }

        if (recipe.getShortDescription().length() > 256 || recipe.getShortDescription().length() <4) {
            throw new RecipeValidationException("Recipe short description must be between 4 and 256 characters!",
                    recipe);
        }

        if (recipe.getLongDescription().length() > 2048 || recipe.getLongDescription().length() < 4) {
            throw new RecipeValidationException("Recipe long description must be between 4 and 2048 characters!",
                    recipe);
        }

        if (recipe.getProducts().trim().isEmpty()) {
            throw new RecipeValidationException("You must add products to this recipe!", recipe);
        }

        if (recipe.getTags().trim().isEmpty()) {
            throw new RecipeValidationException("You must add tags to this recipe!", recipe);
        }

        if (validatePicture && recipe.getPicture().getResource().getFilename().isEmpty()) {
            throw new RecipeValidationException("Recipe must have picture!", recipe);
        }
    }
}