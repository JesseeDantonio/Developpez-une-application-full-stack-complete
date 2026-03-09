package com.openclassrooms.mddapi.service;

import com.openclassrooms.mddapi.dto.in.CreateThemeDTO;
import com.openclassrooms.mddapi.dto.out.ThemeDTO;
import com.openclassrooms.mddapi.entity.ThemeEntity;
import com.openclassrooms.mddapi.entity.UserEntity;
import com.openclassrooms.mddapi.repository.ThemeRepository;
import com.openclassrooms.mddapi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ThemeService {
    private final ThemeRepository themeRepository;
    private final UserRepository userRepository;

    public ThemeService(ThemeRepository themeRepository, UserRepository userRepository) {
        this.themeRepository = themeRepository;
        this.userRepository = userRepository;
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

    // S'abonner à un thème
    @Transactional
    public void subscribe(Integer userId, Integer themeId) {
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        ThemeEntity theme = themeRepository.findById(themeId)
                .orElseThrow(() -> new RuntimeException("Theme not found"));

        // Hibernate gère l'insertion dans la table theme_subscription
        user.getSubscribedThemes().add(theme);
        userRepository.save(user);
    }

    // Se désabonner
    @Transactional
    public void unsubscribe(Integer userId, Integer themeId) {
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        ThemeEntity theme = themeRepository.findById(themeId)
                .orElseThrow(() -> new RuntimeException("Theme not found"));

        user.getSubscribedThemes().remove(theme);
        userRepository.save(user);
    }

    // Récupérer les abonnements d'un utilisateur (pour le profil)
    @Transactional(readOnly = true)
    public List<ThemeDTO> getSubscribedThemes(Integer userId) {
        UserEntity user = userRepository.findById(userId).orElseThrow();
        return user.getSubscribedThemes().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
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
