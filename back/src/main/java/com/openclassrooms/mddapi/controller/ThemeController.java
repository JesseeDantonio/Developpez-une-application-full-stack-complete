package com.openclassrooms.mddapi.controller;

import com.openclassrooms.mddapi.dto.in.CreateThemeDTO;
import com.openclassrooms.mddapi.dto.out.ThemeDTO;
import com.openclassrooms.mddapi.feature.JsonWebToken;
import com.openclassrooms.mddapi.service.ThemeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/themes")
public class ThemeController {
    private final ThemeService themeService;

    public ThemeController(ThemeService themeService) {
        this.themeService = themeService;
    }

    private Integer getUserIdFromPrincipal(Principal principal) {
        return Integer.parseInt(principal.getName());
    }

    // GET /api/themes/{id}
    @GetMapping("/{id}")
    public ResponseEntity<ThemeDTO> getThemeById(@PathVariable Integer id) {
        ThemeDTO themeDTO = themeService.getThemeById(id);
        return ResponseEntity.ok(themeDTO);
    }

    // GET /api/themes
    @GetMapping
    public ResponseEntity<List<ThemeDTO>> getAllThemes() {
        List<ThemeDTO> themes = themeService.getAllThemes();
        return ResponseEntity.ok(themes);
    }

    // POST /api/themes
    @PostMapping
    public ResponseEntity<CreateThemeDTO> createTheme(@RequestBody CreateThemeDTO themeDto) {
        CreateThemeDTO createdTheme = themeService.createTheme(themeDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdTheme);
    }

    // PUT /api/themes/{id}
    @PutMapping("/{id}")
    public ResponseEntity<ThemeDTO> updateTheme(@PathVariable Integer id, @RequestBody CreateThemeDTO themeDto) {
        ThemeDTO updatedTheme = themeService.updateTheme(id, themeDto);
        return ResponseEntity.ok(updatedTheme);
    }

    // DELETE /api/themes/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Integer id) {
        themeService.deleteTheme(id);
        return ResponseEntity.noContent().build();
    }

    // POST /api/themes/{id}/subscribe
    @PostMapping("/{id}/subscribe")
    public ResponseEntity<Void> subscribe(@PathVariable Integer id, Principal principal) {
        // Récupérer l'ID de l'utilisateur connecté via le Principal ou le JWT
        Integer userId = getUserIdFromPrincipal(principal);

        themeService.subscribe(userId, id);
        return ResponseEntity.ok().build();
    }

    // DELETE /api/themes/{id}/subscribe
    @DeleteMapping("/{id}/subscribe")
    public ResponseEntity<Void> unsubscribe(@PathVariable Integer id, Principal principal) {
        Integer userId = getUserIdFromPrincipal(principal);

        themeService.unsubscribe(userId, id);
        return ResponseEntity.ok().build();
    }

    // GET /api/themes/{id}/subscribers
    @GetMapping("/{id}/subscribers")
    public ResponseEntity<List<ThemeDTO>> getSubscribers(@PathVariable Integer id) {
        List<ThemeDTO> themeDTOS = themeService.getSubscribedThemes(id);
        return ResponseEntity.ok(themeDTOS);
    }
}
