package com.example.demo.service;

import com.example.demo.internal.Constants;
import com.example.demo.internal.Validator;
import com.example.demo.model.ValidationResult;
import com.example.demo.model.recipe.Recipe;
import com.example.demo.model.recipe.request.RecipeCreateModel;
import com.example.demo.model.recipe.request.RecipeRequestDto;
import com.example.demo.model.user.User;
import com.example.demo.respository.RecipeRepository;
import com.example.demo.util.LoggedUser;
import com.example.demo.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.sql.rowset.serial.SerialBlob;
import java.sql.Blob;
import java.util.Base64;

@Service
public class RecipeService {
    @Autowired
    private RecipeRepository recipeRepository;
    @Autowired
    private StringUtil stringUtil;
    @Autowired
    private LoggedUser loggedUser;
    @Autowired
    private Validator validator;

    public String getAllRecipes(RecipeRequestDto requestDto) {
        requestDto.getModel().addAttribute(Constants.ATTR_STRINGUTIL, stringUtil);
        requestDto.getModel().addAttribute(Constants.ATTR_LOGGEDUSER, requestDto.getSession().getAttribute(Constants.ATTR_LOGGEDUSER));
        requestDto.getModel().addAttribute(Constants.ATTR_LOGGEDUSERUTIL, loggedUser);
        requestDto.getModel().addAttribute(Constants.ATTR_RECIPES, recipeRepository.findAll());
        return Constants.TMPL_RECIPES;
    }

    public String getRecipeForm(RecipeRequestDto requestDto) {
        requestDto.getModel().addAttribute(Constants.ATTR_STRINGUTIL, stringUtil);
        requestDto.getModel().addAttribute(Constants.ATTR_LOGGEDUSER, requestDto.getSession().getAttribute(Constants.ATTR_LOGGEDUSER));
        requestDto.getModel().addAttribute(Constants.ATTR_LOGGEDUSERUTIL, loggedUser);

        // edit
        if (requestDto.getRecipeId() != null) {
            Recipe recipe = recipeRepository.findById(requestDto.getRecipeId()).orElse(null);
            if (recipe == null) {
                requestDto.getModel().addAttribute(Constants.ATTR_ERROR, "Recipe not found with id: " + requestDto.getRecipeId());
                return Constants.REDIRECT;
            }
            requestDto.getModel().addAttribute(Constants.ATTR_RECIPE, recipe);
            requestDto.getModel().addAttribute(Constants.ATTR_TITLE, "Edit Recipe");
        }
        else {
            requestDto.getModel().addAttribute(Constants.ATTR_TITLE, "Create recipe");
        }

        return Constants.TMPL_RECIPE_FORM;
    }

    public String createRecipe(RecipeRequestDto requestDto) {
        requestDto.getModel().addAttribute(Constants.ATTR_STRINGUTIL, stringUtil);
        User currentlyLoggedUser = (User) requestDto.getSession().getAttribute(Constants.ATTR_LOGGEDUSER);
        requestDto.getModel().addAttribute(Constants.ATTR_LOGGEDUSER, currentlyLoggedUser);
        requestDto.getModel().addAttribute(Constants.ATTR_LOGGEDUSERUTIL, loggedUser);

        boolean isEdit = requestDto.getRecipeCreateModel().getId() != null;

        ValidationResult<String> validation = validator.validateRecipe(requestDto.getRecipeCreateModel(), isEdit);
        if (!validation.isSuccess()) {
            requestDto.getModel().addAttribute(Constants.ATTR_RECIPE, requestDto.getRecipeCreateModel());
            requestDto.getModel().addAttribute(Constants.ATTR_ERROR, validation.getResult());
            return Constants.TMPL_RECIPE_FORM;
        }

        validation = handlePicture(requestDto.getRecipeCreateModel(), isEdit);
        if (!validation.isSuccess()) {
            requestDto.getModel().addAttribute(Constants.ATTR_RECIPE, requestDto.getRecipeCreateModel());
            requestDto.getModel().addAttribute(Constants.ATTR_ERROR, validation.getResult());
            return Constants.TMPL_RECIPE_FORM;
        }

        Recipe recipe = Recipe.fromRecipeCreateModel(requestDto.getRecipeCreateModel(), validation.getResult(), currentlyLoggedUser.getId());
        recipeRepository.save(recipe);
        return Constants.REDIRECT;
    }

    public String deleteRecipe(RecipeRequestDto requestDto) {
        recipeRepository.deleteById(requestDto.getRecipeId());
        return Constants.REDIRECT;
    }

    public String editRecipe(RecipeRequestDto requestDto) {
        return Constants.REDIRECT + Constants.ROUTE_RECIPE_FORM + String.format("?id=%d", requestDto.getRecipeId());
    }

    public String getRecipeById(RecipeRequestDto requestDto) {
        requestDto.getModel().addAttribute(Constants.ATTR_STRINGUTIL, stringUtil);
        requestDto.getModel().addAttribute(Constants.ATTR_LOGGEDUSER, requestDto.getSession().getAttribute(Constants.ATTR_LOGGEDUSER));
        requestDto.getModel().addAttribute(Constants.ATTR_LOGGEDUSERUTIL, loggedUser);

        Recipe recipe = recipeRepository.findById(requestDto.getRecipeId()).orElse(null);
        if (recipe == null) {
            requestDto.getModel().addAttribute(Constants.ATTR_ERROR, "Recipe not found with id " + requestDto.getRecipeId());
            return Constants.REDIRECT;
        }

        requestDto.getModel().addAttribute(Constants.ATTR_RECIPE, recipe);
        return Constants.TMPL_RECIPE;
    }

    // helper methods
    private ValidationResult<String> handlePicture(RecipeCreateModel recipeCreateModel, Boolean isEdit) {
        try {
            // if it is edit don't change the picture
            if (isEdit && recipeCreateModel.getPicture().getResource().getFilename().equals("")) {
                Recipe recipe = recipeRepository.findById(recipeCreateModel.getId()).orElse(null);
                if (recipe == null) {
                    return new ValidationResult<>(false, "Recipe not found with id: " + recipeCreateModel.getId());
                }

                return new ValidationResult<>(true, recipe.getPicture());
            }

            Blob pictureBlob = new SerialBlob(recipeCreateModel.getPicture().getBytes());
            String picture = Base64.getEncoder().encodeToString(pictureBlob.getBytes(1, (int) pictureBlob.length()));
            return new ValidationResult<>(true, picture);
        }
        catch (Exception exception) {
            return new ValidationResult<>(false, "System error, please insert another picture");
        }
    }
}
