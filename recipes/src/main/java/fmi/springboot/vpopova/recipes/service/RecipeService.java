package fmi.springboot.vpopova.recipes.service;

import fmi.springboot.vpopova.recipes.model.Recipe;
import fmi.springboot.vpopova.recipes.model.request.RecipeRequestDTO;

import java.util.List;

public interface RecipeService {
    Long saveOrUpdate(Recipe recipe, Long userId);

    Recipe getRecipeById(RecipeRequestDTO recipeRequestDTO);

    List<Recipe> getAllRecipes();

    void deleteRecipeById(RecipeRequestDTO recipeRequestDTO);
}
