package com.openclassrooms.mddapi.controller;

import com.openclassrooms.mddapi.dto.out.ThemeDTO;
import com.openclassrooms.mddapi.service.ThemeService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/themes")
public class ThemeController {
    private final ThemeService themeService;

    public ThemeController(ThemeService themeService) {
        this.themeService = themeService;
    }

    // GET /api/themes
    @GetMapping
    public List<ThemeDTO> getAllThemes() {
        return themeService.getAllThemes();
    }
}
