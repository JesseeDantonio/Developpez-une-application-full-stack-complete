package com.openclassrooms.mddapi.controller;

import com.openclassrooms.mddapi.dto.in.CreateThemeDTO;
import com.openclassrooms.mddapi.dto.out.ThemeDTO;
import com.openclassrooms.mddapi.feature.JsonWebToken;
import com.openclassrooms.mddapi.service.ThemeService;
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
    public ThemeDTO getThemeById(@PathVariable Integer id) {
        return themeService.getThemeById(id);
    }

    // GET /api/themes
    @GetMapping
    public List<ThemeDTO> getAllThemes() {
        return themeService.getAllThemes();
    }

    // POST /api/themes
    @PostMapping
    public CreateThemeDTO createTheme(@RequestBody CreateThemeDTO themeDto) {
        return themeService.createTheme(themeDto);
    }

    // PUT /api/themes/{id}
    @PutMapping("/{id}")
    public ThemeDTO updateTheme(@PathVariable Integer id, @RequestBody CreateThemeDTO themeDto) {
        return themeService.updateTheme(id, themeDto);
    }

    // DELETE /api/themes/{id}
    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Integer id) {
        themeService.deleteTheme(id);
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
    public List<ThemeDTO> getSubscribers(@PathVariable Integer id) {
        return themeService.getSubscribedThemes(id);
    }
}
