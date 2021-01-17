package fmi.springboot.vpopova.recipes.util;

import fmi.springboot.vpopova.recipes.model.request.RecipeRequestDTO;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ResponseEntityUtil {

    public static String RECIPE_LOCATION_VALUE = "/api/recipes/%s";
    public static String USER_LOCATION_VALUE = "/api/users/%s";

    public static ResponseEntity RecipeWithLocationHeader(RecipeRequestDTO recipeRequestDTO, HttpStatus httpStatus) {
        return getResponseEntityWithLocationHeader(RECIPE_LOCATION_VALUE, httpStatus,
                recipeRequestDTO.getRecipeId());
    }

    public static ResponseEntity UserWithLocationHeader(Long userId, HttpStatus httpStatus) {
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
