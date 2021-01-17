package fmi.springboot.vpopova.recipes.model.response;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Lob;
import javax.validation.constraints.Size;

import org.springframework.web.multipart.MultipartFile;

import fmi.springboot.vpopova.recipes.model.AccountStatus;
import fmi.springboot.vpopova.recipes.model.Gender;
import fmi.springboot.vpopova.recipes.model.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;

@Getter
@AllArgsConstructor
public class LoginResponseDTO {
    private Long id;
    private String username;
    private Gender gender;
    private Role userRole;
    private String userInfo;
    private AccountStatus accountStatus = AccountStatus.ACTIVE;
    private Date registrationTime = new Date();
    private Date lastModified = new Date();
    private MultipartFile picture;
}
