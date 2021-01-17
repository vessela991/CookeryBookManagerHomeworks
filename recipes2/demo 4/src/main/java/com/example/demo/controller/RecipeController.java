package com.example.demo.controller;

import java.io.IOException;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.Base64;

import javax.servlet.http.HttpSession;
import javax.sql.rowset.serial.SerialBlob;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.RecipeRepository;
import com.example.demo.UserRepository;
import com.example.demo.exception.NotFoundException;
import com.example.demo.model.Recipe;
import com.example.demo.model.RecipeCreateModel;
import com.example.demo.model.User;
import com.example.demo.util.LoggedUserUtil;
import com.example.demo.util.StringUtil;
import com.example.demo.util.Validator;

@Controller
public class RecipeController {

    @Autowired
    private RecipeRepository recipeRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private Validator validator;

    @PostMapping("/recipe-form")
    public String createRecipe(
            @ModelAttribute("recipe") RecipeCreateModel recipeCreateModel,
            HttpSession session,
            Model model) throws IOException, SQLException {

        User user = (User) session.getAttribute("user");
        model.addAttribute("loggedUser", user);
        model.addAttribute("LoggedUser", new LoggedUserUtil());

        Boolean validatePicture =
                !(recipeCreateModel.getId() != 0 && recipeCreateModel.getPicture().getResource().getFilename().isBlank());
        String picture = "";

        validator.validateRecipe(recipeCreateModel, validatePicture);

        if (validatePicture) {
            Blob pictureBlob = new SerialBlob(recipeCreateModel.getPicture().getBytes());
            picture = Base64.getEncoder().encodeToString(pictureBlob.getBytes(1, (int) pictureBlob.length()));
        }
        else {
            var recipe = recipeRepository.findById(recipeCreateModel.getId())
                    .orElseThrow(() -> new NotFoundException("Recipe not found for Id: " + recipeCreateModel.getId()));
            picture = recipe.getPicture();
        }

        Recipe recipe = Recipe.fromRecipeCreateModel(recipeCreateModel, picture, user.getId());

        recipeRepository.save(recipe);

        return "redirect:/";
    }

    @PostMapping(params = ("deleteId"))
    public String deleteRecipe(@RequestParam("deleteId") Long id) {
        recipeRepository.deleteById(id);
        return "redirect:/";
    }

    @PostMapping(params = ("editId"))
    public String editRecipe(@RequestParam("editId") Long id) {
        return "redirect:/recipe-form?recipeId=" + id;
    }

    @PostMapping(params = ("recipeId"))
    public String getRecipe(@RequestParam("recipeId") Long id) {
        return "redirect:/recipe?recipeId="+id;
    }

    @GetMapping("/recipe-form")
    public ModelAndView getRecipeForm(
            @ModelAttribute("recipe") RecipeCreateModel recipeCreateModel,
            @RequestParam(value="recipeId", required=false) Long recipeId,
            HttpSession session) {
        ModelAndView result = new ModelAndView("recipe-form");
        result.addObject("title", "Create recipe");
        result.addObject("StringUtil", new StringUtil());
        result.addObject("LoggedUser", new LoggedUserUtil());

        User user = (User) session.getAttribute("user");

        if (user != null) {
            result.addObject("loggedUser", user);
        }

        if (recipeId != null) {
            recipeRepository.findById(recipeId).ifPresent(recipe -> result.addObject("recipe", recipe));
            result.addObject("title", "Edit recipe");
        }

        return result;
    }

    @GetMapping("/")
    public ModelAndView getAllRecipes(HttpSession session) {
        ModelAndView result = new ModelAndView("recipes");

        User user = (User) session.getAttribute("user");

        if (user != null) {
            result.addObject("loggedUser", user);
        }

        result.addObject("recipes", recipeRepository.findAll());
        result.addObject("StringUtil", new StringUtil());
        result.addObject("LoggedUser", new LoggedUserUtil());

        return result;
    }

    @GetMapping("recipe")
    public ModelAndView getRecipeById(
            @RequestParam(value="recipeId", required=false) Long recipeId,
            HttpSession session) {

        ModelAndView result = new ModelAndView("recipe");

        User user = (User) session.getAttribute("user");

        if (user != null) {
            result.addObject("loggedUser", user);
        }

        Recipe recipe = recipeRepository.findById(recipeId)
                .orElseThrow(() -> new NotFoundException("Recipe not found for " + "Id: " + recipeId));
        result.addObject("recipe", recipe);

        result.addObject("StringUtil", new StringUtil());
        result.addObject("LoggedUser", new LoggedUserUtil());

        return result;
    }
}
