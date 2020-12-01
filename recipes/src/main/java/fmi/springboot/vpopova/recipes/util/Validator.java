package fmi.springboot.vpopova.recipes.util;

import org.passay.CharacterRule;
import org.passay.EnglishCharacterData;
import org.passay.LengthRule;
import org.passay.PasswordData;
import org.passay.PasswordValidator;
import org.passay.RuleResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import fmi.springboot.vpopova.recipes.exception.ValidationException;
import fmi.springboot.vpopova.recipes.model.User;
import fmi.springboot.vpopova.recipes.model.request.RegisterRequestDTO;
import fmi.springboot.vpopova.recipes.repository.UserRepository;
import fmi.springboot.vpopova.recipes.service.UserService;

@Component
public class Validator {
    private static Integer USER_INFO_MAX_LENGTH = 512;
    private static Integer USERNAME_MAX_LENGTH = 15;

    @Autowired
    private UserRepository userRepository;

    PasswordValidator validator = new PasswordValidator(
            new LengthRule(8,42),
            new CharacterRule(EnglishCharacterData.Digit, 1),
            new CharacterRule(EnglishCharacterData.Special, 1)
    );

    public void validateUserCredentials(RegisterRequestDTO user) {
        RuleResult validation = validator.validate(new PasswordData(user.getPassword()));

        if (userRepository.findByUsername(user.getUsername()) != null) {
            throw new ValidationException("Username already exists!");
        }

        if (user.getUsername().length() > USERNAME_MAX_LENGTH) {
            throw new ValidationException("Username is more than 15 characters!");
        }

        if (user.getUserInfo().length() > USER_INFO_MAX_LENGTH) {
            throw new ValidationException("Information is more than 512 characters!");
        }

        if (!validation.isValid()) {
            throw new ValidationException("Password validation failed. Length must be over 8 characters, must contain"
                    + " at least one digit and at least one special character");
        }
    }
}
