package fmi.springboot.vpopova.recipes.model.request;

import fmi.springboot.vpopova.recipes.model.Gender;
import fmi.springboot.vpopova.recipes.model.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequestDTO {
    private String username;
    private String password;
    private String userInfo;
    private Gender gender;

    public static RegisterRequestDTO fromUser(User user) {
        return new RegisterRequestDTO(
                user.getUsername(),
                user.getPassword(),
                user.getUserInfo(),
                user.getGender()
        );
    }

    public static User toUser(RegisterRequestDTO model) {
        User user = new User();
        user.setUsername(model.getUsername());
        user.setPassword(model.getPassword());
        user.setUserInfo(model.getUserInfo());
        user.setGender(model.getGender());
        return user;
    }
}
