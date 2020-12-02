package fmi.springboot.vpopova.recipes.model;

import java.util.Date;
import java.util.List;

import javax.validation.constraints.Size;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.web.multipart.MultipartFile;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Document("recipes")
public class Recipe {
    @Id
    private String id;
    private String userId;
    @NonNull
    @Size(max = 80)
    private String name;
    @NonNull
    @Size(max = 256)
    private String shortDescription;
    @NonNull
    private int timeToCook;
    @NonNull
    private List<String> products;
    @Size(max = 2048)
    private String longDescription;
    private List<String> tags;
    private Date shareTime = new Date();
    private Date lastModified = new Date();
}
