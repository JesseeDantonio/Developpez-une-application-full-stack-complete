package com.openclassrooms.mddapi.service;

import com.openclassrooms.mddapi.dto.in.CreateThemeDTO;
import com.openclassrooms.mddapi.dto.out.ThemeDTO;
import com.openclassrooms.mddapi.entity.ThemeEntity;
import com.openclassrooms.mddapi.repository.ThemeRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
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

    public ThemeDTO getThemeById(Integer id) {
        return toDTO(themeRepository.findById(id).orElseThrow());
    }

    public CreateThemeDTO createTheme(CreateThemeDTO themeDto) {
        themeDto.setCreatedAt(LocalDate.now().toString());
        themeDto.setUpdatedAt(LocalDate.now().toString());
        themeRepository.save(toEntity(themeDto));
        return themeDto;
    }

    public ThemeDTO updateTheme(Integer id, CreateThemeDTO createThemeDTO) {
        ThemeEntity existingTh = themeRepository.findById(id).orElse(null);

        if (existingTh != null) {
            existingTh.setName(createThemeDTO.getName());
            existingTh.setDescription(createThemeDTO.getDescription());
            existingTh.setUpdatedAt(LocalDate.now().toString());
            themeRepository.save(existingTh);
            return toDTO(existingTh);
        } else {
            return null;
        }
    }

    public void deleteTheme(Integer id) {
        themeRepository.deleteById(id);
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

    private ThemeEntity toEntity(ThemeDTO dto) {
        ThemeEntity entity = new ThemeEntity();
        entity.setId(dto.getId());
        entity.setName(dto.getName());
        entity.setDescription(dto.getDescription());
        entity.setCreatedAt(dto.getCreatedAt());
        entity.setUpdatedAt(dto.getUpdatedAt());
        return entity;
    }

    private ThemeEntity toEntity(CreateThemeDTO dto) {
        ThemeEntity entity = new ThemeEntity();
        entity.setName(dto.getName());
        entity.setDescription(dto.getDescription());
        entity.setCreatedAt(dto.getCreatedAt());
        entity.setUpdatedAt(dto.getUpdatedAt());
        return entity;

    }
}
