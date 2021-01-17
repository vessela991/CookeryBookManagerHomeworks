package fmi.springboot.vpopova.recipes.service;

import fmi.springboot.vpopova.recipes.model.User;
import fmi.springboot.vpopova.recipes.model.request.LoginRequestDTO;
import fmi.springboot.vpopova.recipes.model.request.RegisterRequestDTO;
import fmi.springboot.vpopova.recipes.model.response.LoginResponseDTO;
import fmi.springboot.vpopova.recipes.model.response.RegisterResponseDTO;

import java.util.List;

public interface UserService {
    User saveOrUpdate(User user);

    User getUserById(Long id);

    List<User> getUsers();

    void deleteUserById(Long id);

    User findUserByUsername(String username);

    LoginResponseDTO login(LoginRequestDTO loginRequestDTO);

    RegisterResponseDTO register(RegisterRequestDTO registerRequestDTO);

    RegisterResponseDTO registerAdmin(RegisterRequestDTO registerRequestDTO);
}
