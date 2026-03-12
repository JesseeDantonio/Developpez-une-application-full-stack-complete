package com.openclassrooms.mddapi.dto.in.auth;

import lombok.Data;

@Data
public class UserAuthDTO {
    private String identifiant;
    private String password;
}
