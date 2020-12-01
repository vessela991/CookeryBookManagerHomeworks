package fmi.springboot.vpopova.recipes.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import fmi.springboot.vpopova.recipes.model.Recipe;
import fmi.springboot.vpopova.recipes.model.request.RecipeRequestDTO;
import fmi.springboot.vpopova.recipes.service.RecipeService;
import fmi.springboot.vpopova.recipes.util.ResponseEntityUtil;

@RestController
@RequestMapping("/api/recipes")
public class RecipeController {
    @Autowired
    RecipeService recipeService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Recipe> getAllUserRecipes() {
        return recipeService.getAllRecipes();
    }

    @PostMapping
    public ResponseEntity createRecipe(@RequestBody Recipe recipe, @RequestHeader("userId") String userId) {
        recipe.setUserId(userId);
        String recipeId = recipeService.saveOrUpdate(recipe);

        return ResponseEntityUtil.RecipeWithLocationHeader(new RecipeRequestDTO(recipeId, userId), HttpStatus.CREATED);
    }

    @GetMapping("/{recipeId}")
    @ResponseStatus(HttpStatus.OK)
    public Recipe getRecipeById(@PathVariable String recipeId, @RequestHeader("userId") String userId) {
        return recipeService.getRecipeById(new RecipeRequestDTO(recipeId, "userId"));
    }

    @PutMapping("/{recipeId}")
    public ResponseEntity updateRecipeById(@RequestHeader("userId") String userId, @RequestBody Recipe recipe) {
        recipe.setUserId(userId);
        String recipeId = recipeService.saveOrUpdate(recipe);

        return ResponseEntityUtil.RecipeWithLocationHeader(new RecipeRequestDTO(recipeId, userId), HttpStatus.OK);

    }

    @DeleteMapping("/{recipeId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteRecipeById(@PathVariable String recipeId, @RequestHeader("userId") String userId) {
        recipeService.deleteRecipeById(new RecipeRequestDTO(recipeId, userId));
    }
}
