package com.example.demo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.example.demo.model.RecipeCreateModel;
import lombok.Getter;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
@Getter
public class RecipeValidationException extends RuntimeException {

    private final RecipeCreateModel recipe;

    public RecipeValidationException(String message, RecipeCreateModel recipe) {
        super(message);
        this.recipe = recipe;
    }
}