package com.example.demo.model;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "recipes")
public class Recipe {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;
    private Long userId;
    @NonNull
    @Column(length = 80)
    private String name;
    @NonNull
    @Column(length = 256)
    private String shortDescription;
    @NonNull
    private Integer timeToCook;
    @NonNull
    @ElementCollection(targetClass=String.class)
    private List<String> products;
    @Column(length = 2048)
    private String longDescription;
    @ElementCollection(targetClass=String.class)
    private List<String> tags;
    private Date shareTime = new Date();
    private Date lastModified = new Date();
    @NonNull
    @Column(length = Integer.MAX_VALUE)
    private String picture;

    public static Recipe fromRecipeCreateModel(RecipeCreateModel recipeCreateModel, String picture, Long userId) {
        Recipe recipe = new Recipe();
        if (recipeCreateModel.getId() != 0) {
            recipe.setId(recipeCreateModel.getId());
        }
        recipe.setName(recipeCreateModel.getName());
        recipe.setLongDescription(recipeCreateModel.getLongDescription());
        recipe.setShortDescription(recipeCreateModel.getShortDescription());
        recipe.setPicture(picture);
        recipe.setTags(Arrays.asList(recipeCreateModel.getTags().split(",")));
        recipe.setTimeToCook(recipeCreateModel.getTimeToCook());
        recipe.setProducts(Arrays.asList(recipeCreateModel.getProducts().split(",")));
        recipe.setUserId(userId);
        return recipe;
    }
}
