package fmi.springboot.vpopova.recipes.service;

import java.util.List;

import fmi.springboot.vpopova.recipes.model.Recipe;
import fmi.springboot.vpopova.recipes.model.request.RecipeRequestDTO;

public interface RecipeService {
    String saveOrUpdate(Recipe recipe);

    Recipe getRecipeById(RecipeRequestDTO recipeRequestDTO);

    List<Recipe> getAllRecipes();

    void deleteRecipeById(RecipeRequestDTO recipeRequestDTO);
}
