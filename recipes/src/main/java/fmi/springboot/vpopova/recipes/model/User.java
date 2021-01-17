package fmi.springboot.vpopova.recipes.model;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Size;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    @NonNull
    @Column(unique=true)
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
    @Size(max = 512)
    private String userInfo;
    @NonNull
    private AccountStatus accountStatus = AccountStatus.ACTIVE;
    private Date registrationTime = new Date();
    private Date lastModified = new Date();
    @Lob
    private MultipartFile picture;
}
