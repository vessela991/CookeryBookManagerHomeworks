package com.example.demo;

import org.springframework.data.repository.CrudRepository;

import com.example.demo.model.Recipe;

public interface RecipeRepository extends CrudRepository<Recipe, Long> {
}
