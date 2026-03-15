package com.openclassrooms.mddapi.controller;

import com.openclassrooms.mddapi.dto.in.UserCreateDTO;
import com.openclassrooms.mddapi.dto.in.auth.UserAuthDTO;
import com.openclassrooms.mddapi.dto.out.UserDTO;
import com.openclassrooms.mddapi.dto.out.auth.TokenDTO;
import com.openclassrooms.mddapi.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class  AuthController {
    private final AuthService authService;
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    // Inscription
    @PostMapping("/register")
    public ResponseEntity<TokenDTO> register(@RequestBody UserCreateDTO userCreateDTO) {
        TokenDTO tokenDTO = authService.register(userCreateDTO);
        return ResponseEntity.ok(tokenDTO);
    }

    // Connexion
    @PostMapping("/login")
    public ResponseEntity<TokenDTO> login(@RequestBody UserAuthDTO userAuthDTO) {
        TokenDTO tokens = authService.login(userAuthDTO);
        return ResponseEntity.ok(tokens);
    }

    // Infos utilisateur (profil)
    @GetMapping("/me")
    public ResponseEntity<UserDTO> me() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        UserDTO user = authService.me(email);
        return ResponseEntity.ok(user);
    }
}
