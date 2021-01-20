package com.example.demo.internal;

import com.example.demo.model.recipe.request.RecipeCreateModel;
import com.example.demo.respository.UserRepository;
import org.passay.CharacterRule;
import org.passay.EnglishCharacterData;
import org.passay.LengthRule;
import org.passay.PasswordData;
import org.passay.PasswordValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.demo.model.user.User;
import com.example.demo.model.ValidationResult;
import com.example.demo.model.user.request.UserLoginModel;
import com.example.demo.model.user.request.UserRegisterModel;

@Component
public class Validator {
    @Autowired
    UserRepository userRepository;

    private static final Integer USER_INFO_MAX_LENGTH = 512;
    private static final Integer USERNAME_MIN_LENGTH = 2;
    private static final Integer USERNAME_MAX_LENGTH = 15;
    private static final Integer PASSWORD_MIN_LENGTH = 8;
    private static final Integer PASSWORD_MAX_LENGTH = 42;
    private static final Integer PASSWORD_NUMBER_SPECIAL_CHARACTERS = 1;

    private static final PasswordValidator validator = new PasswordValidator(
            new LengthRule(PASSWORD_MIN_LENGTH, PASSWORD_MAX_LENGTH),
            new CharacterRule(EnglishCharacterData.Digit, PASSWORD_NUMBER_SPECIAL_CHARACTERS),
            new CharacterRule(EnglishCharacterData.Special, PASSWORD_NUMBER_SPECIAL_CHARACTERS)
    );

    public ValidationResult<String> validateUserRegistration(UserRegisterModel userRegisterModel, boolean isEdit) {
        if (!isEdit && userRepository.findByUsername(userRegisterModel.getUsername()) != null) {
            return new ValidationResult<>(false, "Username is already taken!");
        }

        if (userRegisterModel.getUsername().length() > USERNAME_MAX_LENGTH &&
                userRegisterModel.getUsername().length() < USERNAME_MIN_LENGTH) {
            return new ValidationResult<>(false, "Username should be between 2 and 15 characters!");
        }

        if (userRegisterModel.getUserInfo().length() > USER_INFO_MAX_LENGTH) {
            return new ValidationResult<>(false,"Information is more than 512 characters!");
        }

        if (userRegisterModel.getGender() == null) {
            return new ValidationResult<>(false,"Gender must be filled!");
        }

        if (!validator.validate(new PasswordData(userRegisterModel.getPassword())).isValid()) {
            return new ValidationResult<>(false, "Password validation failed. Length must be over 8 characters, must contain"
                    + " at least one digit and at least one special character");
        }

        return new ValidationResult<>(true);
    }

    public ValidationResult<String> validateUserLogin(UserLoginModel userLoginModel, User user) {
        if(user == null) {
            return new ValidationResult<>(false, "Failed Login");
        }

        if(user.getPassword() == null) {
            return new ValidationResult<>(false, "Failed Login");
        }

        if (!(user.getUsername().equals(userLoginModel.getUsername()) &&
                user.getPassword().equals(userLoginModel.getPassword()))) {
            return new ValidationResult<>(false, "Failed Login");
        }

        return new ValidationResult<>(true);
    }

    public ValidationResult<String> validateRecipe(RecipeCreateModel recipe, Boolean isEdit) {
        if (recipe.getName().length() > 80 || recipe.getName().length() < 4) {
            return new ValidationResult<>(false, "Recipe name must be between 4 and 80 characters!");
        }

        if (recipe.getTimeToCook() <= 0) {
            return new ValidationResult<>(false, "You must add time to cook for this recipe!");
        }

        if (recipe.getShortDescription().length() > 256 || recipe.getShortDescription().length() <4) {
            return new ValidationResult<>(false, "Recipe short description must be between 4 and 256 characters!");
        }

        if (recipe.getLongDescription().length() > 2048 || recipe.getLongDescription().length() < 4) {
            return new ValidationResult<>(false, "Recipe long description must be between 4 and 2048 characters!");
        }

        if (recipe.getProducts().trim().isEmpty()) {
            return new ValidationResult<>(false, "You must add products to this recipe!");
        }

        if (recipe.getTags().trim().isEmpty()) {
            return new ValidationResult<>(false, "You must add tags to this recipe!");
        }

        if (!isEdit && recipe.getPicture() != null && recipe.getPicture().getResource().getFilename().equals("")) {
            return new ValidationResult<>(false, "Recipe must have picture!");
        }

        return new ValidationResult<>(true);
    }
}