package com.openclassrooms.mddapi.service;

import com.openclassrooms.mddapi.dto.in.CreateThemeDTO;
import com.openclassrooms.mddapi.dto.out.ThemeDTO;
import com.openclassrooms.mddapi.entity.ThemeEntity;
import com.openclassrooms.mddapi.entity.UserEntity;
import com.openclassrooms.mddapi.exception.ResourceNotFoundException;
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
import java.util.Optional;
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
        Optional<ThemeEntity> theme = themeRepository.findById(id);

        if (theme.isEmpty()) {
            throw new ResourceNotFoundException("Theme not found");
        }

        return toDTO(theme.get());
    }

    public CreateThemeDTO createTheme(CreateThemeDTO themeDto) {
        themeDto.setCreatedAt(LocalDate.now().toString());
        themeDto.setUpdatedAt(LocalDate.now().toString());
        themeRepository.save(toEntity(themeDto));
        return themeDto;
    }

    public ThemeDTO updateTheme(Integer id, CreateThemeDTO createThemeDTO) {
        Optional<ThemeEntity> existingTh = themeRepository.findById(id);

        if (existingTh.isEmpty()) {
            throw new ResourceNotFoundException("Theme not found");
        }

        existingTh.get().setName(createThemeDTO.getName());
        existingTh.get().setDescription(createThemeDTO.getDescription());
        existingTh.get().setUpdatedAt(LocalDate.now().toString());
        themeRepository.save(existingTh.get());

        return toDTO(existingTh.get());
    }

    public void deleteTheme(Integer id) {
        themeRepository.deleteById(id);
    }

    // S'abonner à un thème
    @Transactional
    public void subscribe(Integer userId, Integer themeId) {
        Optional<UserEntity> user = userRepository.findById(userId);
        if (user.isEmpty()) {
            throw new ResourceNotFoundException("User not found");
        }

        Optional<ThemeEntity> theme = themeRepository.findById(themeId);
        if (theme.isEmpty()) {
            throw new ResourceNotFoundException("Theme not found");
        }

        // Hibernate gère l'insertion dans la table theme_subscription
        user.get().getSubscribedThemes().add(theme.get());
        userRepository.save(user.get());
    }

    // Se désabonner
    @Transactional
    public void unsubscribe(Integer userId, Integer themeId) {
        Optional<UserEntity> user = userRepository.findById(userId);
        if (user.isEmpty()) {
            throw new ResourceNotFoundException("User not found");
        }

        Optional<ThemeEntity> theme = themeRepository.findById(themeId);
        if (theme.isEmpty()) {
            throw new ResourceNotFoundException("Theme not found");
        }

        user.get().getSubscribedThemes().remove(theme.get());
        userRepository.save(user.get());
    }

    // Récupérer les abonnements d'un utilisateur (pour le profil)
    @Transactional(readOnly = true)
    public List<ThemeDTO> getSubscribedThemes(Integer userId) {
        Optional<UserEntity> user = userRepository.findById(userId);
        if (user.isEmpty()) {
            throw new ResourceNotFoundException("User not found");
        }
        return user.get().getSubscribedThemes().stream()
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
