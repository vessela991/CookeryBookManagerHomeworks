package fmi.springboot.vpopova.recipes.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fmi.springboot.vpopova.recipes.exception.NotFoundException;
import fmi.springboot.vpopova.recipes.model.Recipe;
import fmi.springboot.vpopova.recipes.model.request.RecipeRequestDTO;
import fmi.springboot.vpopova.recipes.repository.RecipeRepository;
import fmi.springboot.vpopova.recipes.service.RecipeService;

@Service
public class RecipeServiceImpl implements RecipeService {
    @Autowired
    RecipeRepository recipeRepository;

    @Override
    public String saveOrUpdate(Recipe recipe) {
        return recipeRepository.save(recipe).getId();
    }

    @Override
    public Recipe getRecipeById(RecipeRequestDTO request) {
        return recipeRepository.findById(request.getRecipeId())
                .orElseThrow(() ->
                        new NotFoundException(
                                String.format("Recipe with id %s " + "not found", request.getRecipeId())));
    }

    @Override
    public List<Recipe> getAllRecipes() {
        return recipeRepository.findAll();
    }

    @Override
    public void deleteRecipeById(RecipeRequestDTO request) {
        recipeRepository.deleteById(request.getRecipeId());
    }
}
