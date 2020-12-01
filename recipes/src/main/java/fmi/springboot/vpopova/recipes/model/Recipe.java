package fmi.springboot.vpopova.recipes.model;

import java.util.Date;
import java.util.List;

import javax.validation.constraints.Size;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Document("recipes")
public class Recipe {
    @Id
    private String id;
    private String userId;
    @Size(max = 80)
    private String name;
    @Size(max = 256)
    private String shortDescription;
    private int timeToCook;
    private List<String> products;
    @Size(max = 2048)
    private String longDescription;
    private List<String> tags;
    private Date shareTime = new Date();
    private Date lastModified = new Date();
    private MultipartFile picture;
}
