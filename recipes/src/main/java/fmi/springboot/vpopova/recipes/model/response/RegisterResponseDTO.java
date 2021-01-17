package fmi.springboot.vpopova.recipes.model.response;

import fmi.springboot.vpopova.recipes.model.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RegisterResponseDTO {
    private Long id;

    public static RegisterResponseDTO fromUser(User user) {
        RegisterResponseDTO response = new RegisterResponseDTO();

        response.setId(user.getId());

        return response;
    }
}
