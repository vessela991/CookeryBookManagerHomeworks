package fmi.springboot.vpopova.recipes.model.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LoginResponseDTO {
    private final String jwt;
}
