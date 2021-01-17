package fmi.springboot.vpopova.recipes.controller;

import javax.servlet.http.HttpSession;

import fmi.springboot.vpopova.recipes.model.User;
import fmi.springboot.vpopova.recipes.model.request.LoginRequestDTO;
import fmi.springboot.vpopova.recipes.model.request.RegisterRequestDTO;
import fmi.springboot.vpopova.recipes.model.response.LoginResponseDTO;
import fmi.springboot.vpopova.recipes.model.response.RegisterResponseDTO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping("/api")
public class IdentityController {

    @Autowired
    private IdentityService identityService;

    @RequestMapping("/login")
    public ModelAndView loginHere(HttpSession session, @ModelAttribute("user") User
            user, Model model) {
        ModelAndView mv = new ModelAndView("/home");

        return mv;
    }

    @PostMapping("/login")
    public LoginResponseDTO login(HttpSession session,@RequestBody LoginRequestDTO model) {
        session.
        return identityService.login(model);
    }

    @PostMapping("/user-form")
    public String register(
            @Valid @ModelAttribute ("user") User user,
            BindingResult errors,
            @RequestParam(value = "file", required = false) MultipartFile file,
            Model model,
            @NotNull
                    Authentication auth) {
        User loggedIn = (User)auth.getPrincipal();
        model.addAttribute("fileError", null);
        if(file != null && !file.isEmpty() && file.getOriginalFilename().length() > 4) {
            if (Pattern.matches(".+\\.(png|jpg|jpeg)", file.getOriginalFilename())) {
                handleFile(file, user);
            } else {
                model.addAttribute("fileError", "Submit PNG or JPG picture please!");
                return "user-form";
            }
        }
        if(loggedIn == null) {
            errors.addError(new ObjectError("user", "No authenticated user"));
            return "user-form";
        }
        if(errors.hasErrors()
                && !(user.getId() != null && "".equals(user.getPassword()) && errors.getFieldErrorCount()==1 && errors.getFieldError("password") != null)) { // password error in edit mode is ignored
            return "user-form";
        }
        if(user.getId() == null) {  // create
            log.info("Create new user: {}", user);
            usersService.add(user);
        } else { //edit
            log.info("Edit user: {}", user);
            usersService.update(user);
        }
        return "redirect:/users";
    }


    @PostMapping("/register-admin")
    public RegisterResponseDTO registers(@RequestBody RegisterRequestDTO model) {
        return identityService.registerAdmin(model);
    }
}