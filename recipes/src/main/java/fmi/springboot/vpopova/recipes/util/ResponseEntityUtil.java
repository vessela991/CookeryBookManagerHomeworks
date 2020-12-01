package fmi.springboot.vpopova.recipes.util;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import fmi.springboot.vpopova.recipes.model.request.RecipeRequestDTO;

public class ResponseEntityUtil {

    public static String RECIPE_LOCATION_VALUE = "/api/users/%s/recipes/%s";
    public static String USER_LOCATION_VALUE = "/api/users/%s";

    public static ResponseEntity RecipeWithLocationHeader(RecipeRequestDTO recipeRequestDTO, HttpStatus httpStatus) {
        return getResponseEntityWithLocationHeader(RECIPE_LOCATION_VALUE, httpStatus, recipeRequestDTO.getRecipeId(),
                recipeRequestDTO.getUserId());
    }

    public static ResponseEntity UserWithLocationHeader(String userId, HttpStatus httpStatus) {
        return getResponseEntityWithLocationHeader(USER_LOCATION_VALUE, httpStatus, userId);
    }

    private static ResponseEntity getResponseEntityWithLocationHeader(String locationValue, HttpStatus httpStatus,
            Object... locationReplaceValue) {
        HttpHeaders responseHeaders = new HttpHeaders();

        responseHeaders.set("Location", String.format(locationValue, locationReplaceValue));

        return ResponseEntity.status(httpStatus)
                .headers(responseHeaders)
                .build();
    }
}
