package fmi.springboot.vpopova.recipes.service.impl;

import java.util.List;

import fmi.springboot.vpopova.recipes.exception.UnauthorizedException;
import fmi.springboot.vpopova.recipes.model.Role;
import fmi.springboot.vpopova.recipes.model.User;
import fmi.springboot.vpopova.recipes.repository.UserRepository;
import fmi.springboot.vpopova.recipes.service.UserService;
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

    @Autowired
    UserService userService;

    @Override
    public String saveOrUpdate(Recipe recipe, String userId) {
        if(recipe.getId()==null) {
            return recipeRepository.save(recipe).getId();
        }

        User user = userService.getUserById(userId);
        if (user.getUserRole() == Role.ADMIN || recipe.getUserId().equals(user.getId())) {
            return recipeRepository.save(recipe).getId();
        }
        else throw new UnauthorizedException("You dont have permission to delete this recipe.");
    }

    @Override
    public Recipe getRecipeById(RecipeRequestDTO request) {
        Recipe recipe = recipeRepository.findById(request.getRecipeId())
                .orElseThrow(() ->
                        new NotFoundException(
                                String.format("Recipe with id %s " + "not found", request.getRecipeId())));
        User user = userService.getUserById(request.getUserId());

        if (user.getUserRole() == Role.ADMIN || request.getUserId().equals(user.getId())) {
            return recipe;
        }
        else throw new UnauthorizedException("You dont have permission to delete this recipe.");
    }

    @Override
    public List<Recipe> getAllRecipes() {
        return recipeRepository.findAll();
    }

    @Override
    public void deleteRecipeById(RecipeRequestDTO request) {
        User user = userService.getUserById(request.getUserId());
        if (user.getUserRole() == Role.ADMIN || request.getUserId().equals(user.getId())) {
            recipeRepository.deleteById(request.getRecipeId());
        }
        else throw new UnauthorizedException("You dont have permission to delete this recipe.");
    }
}
