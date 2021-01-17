package fmi.springboot.vpopova.recipes.service.impl;

import fmi.springboot.vpopova.recipes.exception.NotFoundException;
import fmi.springboot.vpopova.recipes.exception.UnauthorizedException;
import fmi.springboot.vpopova.recipes.exception.ValidationException;
import fmi.springboot.vpopova.recipes.model.Role;
import fmi.springboot.vpopova.recipes.model.User;
import fmi.springboot.vpopova.recipes.model.request.LoginRequestDTO;
import fmi.springboot.vpopova.recipes.model.request.RegisterRequestDTO;
import fmi.springboot.vpopova.recipes.model.response.LoginResponseDTO;
import fmi.springboot.vpopova.recipes.model.response.RegisterResponseDTO;
import fmi.springboot.vpopova.recipes.repository.UserRepository;
import fmi.springboot.vpopova.recipes.service.UserService;
import fmi.springboot.vpopova.recipes.util.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    Validator validator;

    @Override
    public User saveOrUpdate(User user) {


        User usr = userRepository.findByUsername(user.getUsername());

        if (user.getId()!=null && !user.getId().equals(usr.getId())) {
            throw new UnauthorizedException("You dont have permission to edit this user.");
        }

        validator.validateUserCredentials(RegisterRequestDTO.fromUser(user));

        if (user.getId() == null) {
            if (userRepository.findByUsername(user.getUsername()) != null) {
                throw new ValidationException("Username already exists!");
            }
        }

        return userRepository.save(user);
    }

    @Override
    public User getUserById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new NotFoundException(String.format("User with id %s "
                + "doesn not exist", id)));
    }

    public List<User> getUsers() {
        return (List<User>) userRepository.findAll();
    }

    @Override
    public void deleteUserById(Long id) {
        userRepository.deleteById(id);
    }

    public String getCurrentUserId(String token) {
        return jwtUtil.getUserId(token);
    }

    @Override
    public User findUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public LoginResponseDTO login(LoginRequestDTO loginRequestDTO) {
        return new LoginResponseDTO(jwt);
    }

    @Override
    public RegisterResponseDTO register(RegisterRequestDTO model) {
        validator.validateUserCredentials(model);
        User user = RegisterRequestDTO.toUser(model);
        user.setUserRole(Role.USER);
        userRepository.save(user);

        return RegisterResponseDTO.fromUser(user);
    }

    @Override
    public RegisterResponseDTO registerAdmin(RegisterRequestDTO model) {
        validator.validateUserCredentials(model);

        User user = RegisterRequestDTO.toUser(model);
        user.setUserRole(Role.ADMIN);
        userRepository.save(user);

        return RegisterResponseDTO.fromUser(user);
    }
}
