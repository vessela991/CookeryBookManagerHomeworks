package fmi.springboot.vpopova.recipes.controller;

import fmi.springboot.vpopova.recipes.model.Recipe;
import fmi.springboot.vpopova.recipes.model.request.RecipeRequestDTO;
import fmi.springboot.vpopova.recipes.service.RecipeService;
import fmi.springboot.vpopova.recipes.service.UserService;
import fmi.springboot.vpopova.recipes.util.JWTUtil;
import fmi.springboot.vpopova.recipes.util.ResponseEntityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/recipes")
public class RecipeController {
    @Autowired
    RecipeService recipeService;

    @Autowired
    UserService userService;

    @Autowired
    JWTUtil jwtUtil;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Recipe> getAllUserRecipes() {
        return recipeService.getAllRecipes();
    }

    @PostMapping
    public ResponseEntity createRecipe(@RequestBody Recipe recipe) {
        setUserIdForRecipe(recipe);
        String userId = getLoggedUserId();
        String recipeId = recipeService.saveOrUpdate(recipe, userId);
        return ResponseEntityUtil.RecipeWithLocationHeader(new RecipeRequestDTO(recipeId, userId), HttpStatus.CREATED);
    }

    private void setUserIdForRecipe(@RequestBody Recipe recipe) {
        String userId = getLoggedUserId();
        recipe.setUserId(userId);
    }

    private String getLoggedUserId() {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        String username = userDetails.getUsername();
        return userService.findUserByUsername(username).getId();
    }

    @GetMapping("/{recipeId}")
    @ResponseStatus(HttpStatus.OK)
    public Recipe getRecipeById(@PathVariable String recipeId) {
        return recipeService.getRecipeById(new RecipeRequestDTO(recipeId, getLoggedUserId()));
    }

    @PutMapping("/{recipeId}")
    public ResponseEntity updateRecipeById(@RequestBody Recipe recipe, @PathVariable("recipeId") String recipeId) {
        String userId = getLoggedUserId();
        recipe.setId(recipeId);
        recipeService.saveOrUpdate(recipe, userId);
        return ResponseEntityUtil.RecipeWithLocationHeader(new RecipeRequestDTO(recipeId, userId), HttpStatus.OK);
    }

    @DeleteMapping("/{recipeId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteRecipeById(@PathVariable String recipeId) {
        recipeService.deleteRecipeById(new RecipeRequestDTO(recipeId, getLoggedUserId()));
    }
}
