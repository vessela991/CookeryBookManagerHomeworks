package fmi.springboot.vpopova.recipes.service;

import fmi.springboot.vpopova.recipes.model.UserPrincipal;
import fmi.springboot.vpopova.recipes.model.request.LoginRequestDTO;
import fmi.springboot.vpopova.recipes.model.request.RegisterRequestDTO;
import fmi.springboot.vpopova.recipes.model.response.LoginResponseDTO;
import fmi.springboot.vpopova.recipes.model.response.RegisterResponseDTO;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface IdentityService extends UserDetailsService {
    LoginResponseDTO login(LoginRequestDTO loginRequestDTO);

    RegisterResponseDTO register(RegisterRequestDTO registerRequestDTO);

    @Override
    UserPrincipal loadUserByUsername(String s);
}
