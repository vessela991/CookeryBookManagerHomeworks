package fmi.springboot.vpopova.recipes.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fmi.springboot.vpopova.recipes.model.request.LoginRequestDTO;
import fmi.springboot.vpopova.recipes.model.request.RegisterRequestDTO;
import fmi.springboot.vpopova.recipes.model.response.LoginResponseDTO;
import fmi.springboot.vpopova.recipes.model.response.RegisterResponseDTO;
import fmi.springboot.vpopova.recipes.service.IdentityService;

@RestController
@RequestMapping("/api")
public class IdentityController {

    @Autowired
    private IdentityService identityService;

    @PostMapping("/login")
    public LoginResponseDTO login(@RequestBody LoginRequestDTO model) {
        return identityService.login(model);
    }

    @PostMapping("/register")
    public RegisterResponseDTO register(@RequestBody RegisterRequestDTO model) {
        return identityService.register(model);
    }

}