package com.openclassrooms.mddapi.controller;

import com.openclassrooms.mddapi.dto.in.CreateThemeDTO;
import com.openclassrooms.mddapi.dto.out.ThemeDTO;
import com.openclassrooms.mddapi.service.ThemeService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/themes")
public class ThemeController {
    private final ThemeService themeService;

    public ThemeController(ThemeService themeService) {
        this.themeService = themeService;
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
}
