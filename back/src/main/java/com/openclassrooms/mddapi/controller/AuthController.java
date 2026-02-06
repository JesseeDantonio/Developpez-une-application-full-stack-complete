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
public class AuthController {
    private final AuthService authService;
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    // Inscription
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody UserCreateDTO userCreateDTO) {
        try {
            TokenDTO tokens = authService.register(userCreateDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(tokens);
        } catch (RuntimeException e) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(Collections.singletonMap("L'identification a échouée.", e.getMessage()));
        }
    }

    // Connexion
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserAuthDTO userAuthDTO) {
        try {
            TokenDTO tokens = authService.login(userAuthDTO);
            return ResponseEntity.ok(tokens);
        } catch (RuntimeException e) {
            // On peut retourner un objet avec un message
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(Collections.singletonMap("L'identification a échouée.", e.getMessage()));
        }
    }

    // Infos utilisateur (profil)
    @GetMapping("/me")
    public ResponseEntity<?> me() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        try {
            String email = authentication.getName();
            System.out.println(authentication.getCredentials());
            UserDTO user = authService.me(email);
            return ResponseEntity.ok(user);
        } catch (RuntimeException e) {
            Map<String, String> error = Map.of(
                    "error", "Impossible de récupérer le profil.",
                    "details", e.getMessage()
            );
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        }
    }
}
