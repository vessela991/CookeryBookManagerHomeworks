package com.example.demo.controller;

import com.example.demo.model.recipe.request.RecipeCreateModel;
import com.example.demo.model.recipe.request.RecipeRequestDto;
import com.example.demo.service.RecipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;

@Controller
public class RecipeController {

    @Autowired
    private RecipeService recipeService;

    @GetMapping("/")
    public String getAllRecipes(Model model, HttpSession session) {
        return recipeService.getAllRecipes(new RecipeRequestDto(model, session));
    }

    @GetMapping("/recipe")
    public String getRecipe(
            @RequestParam(value = "id") Long recipeId,
            Model model,
            HttpSession session) {
        return recipeService.getRecipeById(new RecipeRequestDto(recipeId, model, session));
    }

    @GetMapping("/recipe-form")
    public String getRecipeForm(
            @ModelAttribute("recipe") RecipeCreateModel requestModel,
            @RequestParam(value = "id", required = false) Long recipeId,
            Model model,
            HttpSession session) {
        return recipeService.getRecipeForm(new RecipeRequestDto(requestModel, recipeId, model, session));
    }

    @PostMapping("/recipe-form")
    public String createRecipe(
            @ModelAttribute("recipe") RecipeCreateModel requestModel,
            Model model,
            HttpSession session) {
        return recipeService.createRecipe(new RecipeRequestDto(requestModel, model, session));
    }

    @PostMapping("/recipe")
    public String deleteRecipe(@RequestParam("id") Long id) {
        return recipeService.deleteRecipe(new RecipeRequestDto(id));
    }

//    @PostMapping(value = "recipe", params = ("editId"))
//    public String editRecipe(@RequestParam("editId") Long id) {
//        return recipeService.editRecipe(new RecipeRequestDto(id));
//    }
//
//    @PostMapping(value = "recipe", params = ("recipeId"))
//    public String editRecipe(@RequestParam("recipeId") Long id, Model model, HttpSession session) {
//        return recipeService.getRecipeById(new RecipeRequestDto(id, model, session));
//    }
}