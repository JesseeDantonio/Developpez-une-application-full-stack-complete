package com.openclassrooms.mddapi.service;

import com.openclassrooms.mddapi.dto.out.ThemeDTO;
import com.openclassrooms.mddapi.entity.ThemeEntity;
import com.openclassrooms.mddapi.repository.ThemeRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ThemeService {
    private final ThemeRepository themeRepository;

    public ThemeService(ThemeRepository themeRepository) {
        this.themeRepository = themeRepository;
    }

    public List<ThemeDTO> getAllThemes() {
        List<ThemeDTO> themes = new ArrayList<>();
        themeRepository.findAll().forEach(rental -> {
            ThemeDTO dto = toDTO(rental);
            themes.add(dto);
        });

        return themes;
    }

    private ThemeDTO toDTO(ThemeEntity entity) {
        ThemeDTO dto = new ThemeDTO();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setDescription(entity.getDescription());
        dto.setCreatedAt(entity.getCreatedAt());
        dto.setUpdatedAt(entity.getUpdatedAt());
        return dto;
    }
}
