package fmi.springboot.vpopova.recipes.model;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import java.util.ArrayList;
import java.util.Date;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="recipe")
public class Recipe {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NonNull
    private Long userId;
    @NonNull
    @Size(max = 80)
    private String name;
    @NonNull
    @Size(max = 256)
    private String shortDescription;
    @NonNull
    private int timeToCook;
    @NonNull
    private ArrayList<String> products;
    @NonNull
    @Lob
    private MultipartFile recipeResult;
    @Size(max = 2048)
    private String longDescription;
    private ArrayList<String> tags;
    private Date shareTime = new Date();
    private Date lastModified = new Date();
}
