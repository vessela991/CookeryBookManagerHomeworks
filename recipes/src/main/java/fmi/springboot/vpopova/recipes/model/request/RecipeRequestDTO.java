package fmi.springboot.vpopova.recipes.model.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class RecipeRequestDTO {
    Long recipeId;
    Long userId;
}
