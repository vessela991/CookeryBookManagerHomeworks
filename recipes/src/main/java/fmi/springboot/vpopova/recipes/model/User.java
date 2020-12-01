package fmi.springboot.vpopova.recipes.model;

import java.util.Date;

import org.springframework.boot.convert.DataSizeUnit;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document("users")
public class User {
    @Id
    private String id;
    @NonNull
    @Indexed(unique=true)
    @Size(max = 15)
    private String username;
    @NonNull
    @Size(min = 8)
    private String password;
    @NonNull
    private Gender gender;
    @NonNull
    private Role userRole = Role.USER;
    @NonNull
    private String userInfo;
    @NonNull
    private AccountStatus accountStatus = AccountStatus.ACTIVE;
    private Date registrationTime = new Date();
    private Date lastModified = new Date();
    private MultipartFile picture;
}