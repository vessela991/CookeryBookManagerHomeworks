package com.example.demo.model;

import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RecipeCreateModel {
    private Long id;
    private String name;
    private String shortDescription;
    private int timeToCook;
    private String products;
    private String longDescription;
    private String tags;
    private MultipartFile picture;
}
