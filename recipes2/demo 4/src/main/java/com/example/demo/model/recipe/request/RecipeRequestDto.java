package com.example.demo.model.recipe.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.ui.Model;

import javax.servlet.http.HttpSession;

@Getter
@Setter
@AllArgsConstructor
public class RecipeRequestDto {
    private RecipeCreateModel recipeCreateModel;
    private Long recipeId;
    private Model model;
    private HttpSession session;

    public RecipeRequestDto(Long recipeId) {
        this.recipeId = recipeId;
    }

    public RecipeRequestDto(Model model, HttpSession session) {
        this.model = model;
        this.session = session;
    }

    public RecipeRequestDto(RecipeCreateModel recipeCreateModel, Model model, HttpSession session) {
        this.recipeCreateModel = recipeCreateModel;
        this.model = model;
        this.session = session;
    }

    public RecipeRequestDto(Long recipeId, Model model, HttpSession session) {
        this.recipeId = recipeId;
        this.model = model;
        this.session = session;
    }
}
