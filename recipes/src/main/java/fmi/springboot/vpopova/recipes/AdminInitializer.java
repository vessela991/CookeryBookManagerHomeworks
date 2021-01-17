package fmi.springboot.vpopova.recipes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;

import fmi.springboot.vpopova.recipes.model.AccountStatus;
import fmi.springboot.vpopova.recipes.model.Gender;
import fmi.springboot.vpopova.recipes.model.Role;
import fmi.springboot.vpopova.recipes.model.User;
import fmi.springboot.vpopova.recipes.service.UserService;

public class AdminInitializer implements ApplicationRunner {

    @Autowired
    UserService userService;

    @Override
    public void run(ApplicationArguments args) {
        if (userService.findUserByUsername("admin") == null) {
            User user = new User();
            user.setUsername("admin");
            user.setPassword("admin1234!");
            user.setGender(Gender.OTHER);
            user.setUserRole(Role.ADMIN);
            user.setUserInfo("admin");
            user.setAccountStatus(AccountStatus.ACTIVE);
            userService.saveOrUpdate(user);
        }
    }
}
