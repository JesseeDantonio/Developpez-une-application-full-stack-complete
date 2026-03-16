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

    /**
     * Récupère un thème par son ID.
     * @param id L'ID du thème
     * @return La réponse HTTP avec les détails du thème
     */
    @GetMapping("/{id}")
    public ResponseEntity<ThemeDTO> getThemeById(@PathVariable Integer id) {
        ThemeDTO themeDTO = themeService.getThemeById(id);
        return ResponseEntity.ok(themeDTO);
    }

    /**
     * Récupère tous les thèmes.
     * @return La réponse HTTP avec la liste des thèmes
     */
    @GetMapping
    public ResponseEntity<List<ThemeDTO>> getAllThemes() {
        List<ThemeDTO> themes = themeService.getAllThemes();
        return ResponseEntity.ok(themes);
    }

    /**
     * Crée un nouveau thème.
     * @param themeDto Les données du nouveau thème
     * @return La réponse HTTP avec les détails du thème créé
     */
    @PostMapping
    public ResponseEntity<CreateThemeDTO> createTheme(@RequestBody CreateThemeDTO themeDto) {
        CreateThemeDTO createdTheme = themeService.createTheme(themeDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdTheme);
    }

    /**
     * Met à jour un thème existant.
     * @param id L'ID du thème à mettre à jour
     * @param themeDto Les données du thème mis à jour
     * @return La réponse HTTP avec les détails du thème mis à jour
     */
    @PutMapping("/{id}")
    public ResponseEntity<ThemeDTO> updateTheme(@PathVariable Integer id, @RequestBody CreateThemeDTO themeDto) {
        ThemeDTO updatedTheme = themeService.updateTheme(id, themeDto);
        return ResponseEntity.ok(updatedTheme);
    }

    /**
     * Supprime un thème.
     * @param id L'ID du thème à supprimer
     * @return La réponse HTTP
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Integer id) {
        themeService.deleteTheme(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Abonne un utilisateur à un thème.
     * @param id L'ID du thème
     * @param principal Le principal contenant les informations de l'utilisateur connecté
     * @return La réponse HTTP
     */
    @PostMapping("/{id}/subscribe")
    public ResponseEntity<Void> subscribe(@PathVariable Integer id, Principal principal) {
        // Récupérer l'ID de l'utilisateur connecté via le Principal ou le JWT
        Integer userId = getUserIdFromPrincipal(principal);

        themeService.subscribe(userId, id);
        return ResponseEntity.ok().build();
    }

    /**
     * Désabonne un utilisateur d'un thème.
     * @param id L'ID du thème
     * @param principal Le principal contenant les informations de l'utilisateur connecté
     * @return La réponse HTTP
     */
    @DeleteMapping("/{id}/subscribe")
    public ResponseEntity<Void> unsubscribe(@PathVariable Integer id, Principal principal) {
        Integer userId = getUserIdFromPrincipal(principal);

        themeService.unsubscribe(userId, id);
        return ResponseEntity.ok().build();
    }

    /**
     * Récupère les utilisateurs abonnés à un thème.
     * @param id L'ID du thème
     * @return La réponse HTTP avec la liste des utilisateurs abonnés
     */
    @GetMapping("/{id}/subscribers")
    public ResponseEntity<List<ThemeDTO>> getSubscribers(@PathVariable Integer id) {
        List<ThemeDTO> themeDTOS = themeService.getSubscribedThemes(id);
        return ResponseEntity.ok(themeDTOS);
    }
}
