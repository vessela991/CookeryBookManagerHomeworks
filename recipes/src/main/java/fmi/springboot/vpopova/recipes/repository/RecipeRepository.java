package fmi.springboot.vpopova.recipes.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import fmi.springboot.vpopova.recipes.model.Recipe;

@Repository
public interface RecipeRepository extends MongoRepository<Recipe, String> {
}
