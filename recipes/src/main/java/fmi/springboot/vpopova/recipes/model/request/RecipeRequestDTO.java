package fmi.springboot.vpopova.recipes.model.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class RecipeRequestDTO {
    String recipeId;
    String userId;
}
