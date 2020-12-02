package fmi.springboot.vpopova.recipes.service.impl;

import fmi.springboot.vpopova.recipes.exception.NotFoundException;
import fmi.springboot.vpopova.recipes.model.User;
import fmi.springboot.vpopova.recipes.model.request.RegisterRequestDTO;
import fmi.springboot.vpopova.recipes.repository.UserRepository;
import fmi.springboot.vpopova.recipes.service.UserService;
import fmi.springboot.vpopova.recipes.util.JWTUtil;
import fmi.springboot.vpopova.recipes.util.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    JWTUtil jwtUtil;
    @Autowired
    Validator validator;

    @Override
    public User saveOrUpdate(User user) {
        validator.validateUserCredentials(RegisterRequestDTO.fromUser(user));
        return userRepository.save(user);
    }

    @Override
    public User getUserById(String id) {
        return userRepository.findById(id).orElseThrow(() -> new NotFoundException(String.format("User with id %s "
                + "doesn not exist", id)));
    }

    public List<User> getUsers() {
        return userRepository.findAll();
    }

    @Override
    public void deleteUserById(String id) {
        userRepository.deleteById(id);
    }

    public String getCurrentUserId(String token) {
        return jwtUtil.getUserId(token);
    }

    @Override
    public User findUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}
