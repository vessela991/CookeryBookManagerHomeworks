package fmi.springboot.vpopova.recipes.service;

import fmi.springboot.vpopova.recipes.model.Recipe;
import fmi.springboot.vpopova.recipes.model.request.RecipeRequestDTO;

import java.util.List;

public interface RecipeService {
    String saveOrUpdate(Recipe recipe, String userId);

    Recipe getRecipeById(RecipeRequestDTO recipeRequestDTO);

    List<Recipe> getAllRecipes();

    void deleteRecipeById(RecipeRequestDTO recipeRequestDTO);
}
