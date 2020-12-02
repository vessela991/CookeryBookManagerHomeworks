package fmi.springboot.vpopova.recipes.service.impl;

import fmi.springboot.vpopova.recipes.model.User;
import fmi.springboot.vpopova.recipes.model.UserPrincipal;
import fmi.springboot.vpopova.recipes.model.request.LoginRequestDTO;
import fmi.springboot.vpopova.recipes.model.request.RegisterRequestDTO;
import fmi.springboot.vpopova.recipes.model.response.LoginResponseDTO;
import fmi.springboot.vpopova.recipes.model.response.RegisterResponseDTO;
import fmi.springboot.vpopova.recipes.repository.UserRepository;
import fmi.springboot.vpopova.recipes.service.IdentityService;
import fmi.springboot.vpopova.recipes.util.JWTUtil;
import fmi.springboot.vpopova.recipes.util.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class IdentityServiceImpl implements IdentityService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JWTUtil jwtUtil;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private Validator validator;

    @Override
    public UserPrincipal loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);

        return new UserPrincipal(user);
    }

    @Override
    public LoginResponseDTO login(LoginRequestDTO loginRequestDTO) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequestDTO.getUsername(),
                loginRequestDTO.getPassword()));

        String jwt = jwtUtil.generateToken(
                loadUserByUsername(loginRequestDTO.getUsername()));

        return new LoginResponseDTO(jwt);
    }

    @Override
    public RegisterResponseDTO register(RegisterRequestDTO model) {
        validator.validateUserCredentials(model);
        User user = userRepository.save(RegisterRequestDTO.toUser(model));

        return RegisterResponseDTO.fromUser(user);
    }
}
