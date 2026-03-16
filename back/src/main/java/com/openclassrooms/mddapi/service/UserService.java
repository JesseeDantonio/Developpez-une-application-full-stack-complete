package com.openclassrooms.mddapi.service;

import com.openclassrooms.mddapi.dto.in.UserCreateDTO;
import com.openclassrooms.mddapi.dto.out.UserDTO;
import com.openclassrooms.mddapi.entity.UserEntity;
import com.openclassrooms.mddapi.exception.ResourceNotFoundException;
import com.openclassrooms.mddapi.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Récupère tous les utilisateurs.
     * @return Une liste de UserDTO représentant les utilisateurs trouvés
     */
    public List<UserDTO> getAllUsers() {
        List<UserEntity> users = userRepository.findAll();
        List<UserDTO> userDTOList = new ArrayList<>();
        for (UserEntity user : users) {
            UserDTO userDTO = new UserDTO();
            userDTO.setId(user.getId());
            userDTO.setName(user.getName());
            userDTO.setEmail(user.getEmail());
            userDTO.setUpdatedAt(user.getUpdatedAt());
            userDTO.setCreatedAt(user.getCreatedAt());
            userDTOList.add(userDTO);
        }
        return userDTOList;
    }

    /**
     * Récupère un utilisateur par son ID.
     * @param id L'ID de l'utilisateur à récupérer
     * @return Un UserDTO représentant l'utilisateur trouvé
     */
    public UserDTO getUserById(Integer id) {
        Optional<UserEntity> user = userRepository.findById(id);
        if (user.isEmpty()) {
            throw new ResourceNotFoundException("User not found");
        }
        return toDTO(user.get());
    }

    /**
     * Crée un nouvel utilisateur.
     * @param user Le DTO contenant les données de l'utilisateur à créer
     * @return Le UserCreateDTO représentant l'utilisateur créé
     */
    public UserCreateDTO createUser(UserCreateDTO user) {
        userRepository.save(toEntity(user));
        return user;
    }

    /**
     * Met à jour un utilisateur existant.
     * @param id L'ID de l'utilisateur à mettre à jour
     * @param user Le DTO contenant les nouvelles données de l'utilisateur
     * @return Le UserDTO représentant l'utilisateur mis à jour
     */
    public UserDTO updateUser(Integer id, UserCreateDTO user) {
        Optional<UserEntity> existingUser = userRepository.findById(id);

        if (existingUser.isEmpty()) {
            throw new ResourceNotFoundException("User not found");
        }

        if (!user.getEmail().isEmpty()) existingUser.get().setEmail(user.getEmail());
        if (!user.getName().isEmpty()) existingUser.get().setName(user.getName());
        if (!user.getPassword().isEmpty()) existingUser.get().setPassword(passwordEncoder.encode(user.getPassword()));

        existingUser.get().setUpdatedAt(LocalDate.now().toString());
        userRepository.save(existingUser.get());

        return toDTO(existingUser.get());
    }

    /**
     * Supprime un utilisateur par son ID.
     * @param id L'ID de l'utilisateur à supprimer
     */
    public void deleteUser(Integer id) {
        userRepository.deleteById(id);
    }

    /**
     * Convertit un UserEntity en UserDTO.
     * @param entity L'entité à convertir
     * @return Le DTO correspondant
     */
    private UserDTO toDTO(UserEntity entity) {
        UserDTO dto = new UserDTO();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setEmail(entity.getEmail());
        dto.setUpdatedAt(entity.getUpdatedAt());
        dto.setCreatedAt(entity.getCreatedAt());
        return dto;
    }

    /**
     * Convertit un UserCreateDTO en UserEntity.
     * @param dto Le DTO à convertir
     * @return L'entité correspondante
     */
    public UserEntity toEntity(UserCreateDTO dto) {
        UserEntity entity = new UserEntity();
        entity.setName(dto.getName());
        entity.setEmail(dto.getEmail());
        entity.setPassword(dto.getPassword());
        entity.setUpdatedAt(LocalDate.now().toString());
        entity.setCreatedAt(LocalDate.now().toString());
        return entity;
    }
}
