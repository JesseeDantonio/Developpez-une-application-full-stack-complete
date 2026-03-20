package com.openclassrooms.mddapi.service;

import com.openclassrooms.mddapi.dto.in.UserCreateDTO;
import com.openclassrooms.mddapi.dto.in.auth.UserAuthDTO;
import com.openclassrooms.mddapi.dto.out.UserDTO;
import com.openclassrooms.mddapi.dto.out.auth.TokenDTO;
import com.openclassrooms.mddapi.entity.UserEntity;
import com.openclassrooms.mddapi.exception.ResourceAlreadyExistException;
import com.openclassrooms.mddapi.exception.ResourceNotFoundException;
import com.openclassrooms.mddapi.feature.JsonWebToken;
import com.openclassrooms.mddapi.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@Service
public class AuthService {
    // 15 min en ms
    long ACCESS_TOKEN_EXPIRATION = 15 * 60 * 1000;

    private final UserRepository userRepo;
    private final PasswordEncoder passwordEncoder;
    private final JsonWebToken jsonWebToken;

    public AuthService(UserRepository userRepo, PasswordEncoder passwordEncoder, JsonWebToken jsonWebToken) {
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
        this.jsonWebToken = jsonWebToken;
    }

    /**
     * Enregistre un nouvel utilisateur.
     * @param userAuth Le DTO contenant les données de l'utilisateur à enregistrer
     * @return Un TokenDTO contenant le token d'accès pour l'utilisateur enregistré
     */
    public TokenDTO register(UserCreateDTO userAuth) {
        Optional<UserEntity> userExist = userRepo.findByEmail(userAuth.getEmail());
        if (userExist.isPresent()) {
            throw new ResourceAlreadyExistException("User already exist");
        }

        if (userAuth.getPassword().isEmpty() || userAuth.getPassword().length() < 8) {
            throw new RuntimeException("Password must be at least 8 characters long");
        }

        if (userAuth.getName().isEmpty() || userAuth.getName().length() < 3) {
            throw new RuntimeException("Name must be at least 3 characters long");
        }

        if (userAuth.getEmail().isEmpty()) {
            throw new RuntimeException("Email must not be empty");
        }

        userAuth.setEmail(userAuth.getEmail());
        userAuth.setName(userAuth.getName());
        userAuth.setPassword(passwordEncoder.encode(userAuth.getPassword()));

        UserEntity userEntity = new UserEntity();
        userEntity.setEmail(userAuth.getEmail());
        userEntity.setName(userAuth.getName());
        userEntity.setPassword(userAuth.getPassword());
        userEntity.setUpdatedAt(LocalDate.now().toString());
        userEntity.setCreatedAt(LocalDate.now().toString());

        UserEntity userCreated = userRepo.save(userEntity);

        return new TokenDTO(
                jsonWebToken.generateToken(userCreated.getId().toString(), userCreated.getName(), ACCESS_TOKEN_EXPIRATION)
        );
    }

    /**
     * Connecte un utilisateur existant.
     * @param userAuth Le DTO contenant les données de connexion de l'utilisateur
     * @return Un TokenDTO contenant le token d'accès pour l'utilisateur connecté
     */
    public TokenDTO login(UserAuthDTO userAuth) {
        Optional<UserEntity> user = userRepo.findByEmailOrName(userAuth.getIdentifiant(), userAuth.getIdentifiant());

        if (user.isEmpty() || !passwordEncoder.matches(userAuth.getPassword(), user.get().getPassword())) {
            throw new RuntimeException("Identifiant ou mot de passe incorrect");
        }

        return new TokenDTO(jsonWebToken.generateToken(user.get().getId().toString(), user.get().getName(), ACCESS_TOKEN_EXPIRATION));
    }

    /**
     * Récupère les informations de l'utilisateur connecté.
     * @param email L'email de l'utilisateur
     * @return Le DTO représentant les informations de l'utilisateur
     */
    public UserDTO me(String email) {
        Optional<UserEntity> user = userRepo.findByEmail(email);
        if (user.isEmpty()) {
            throw new ResourceNotFoundException("User not found");
        }
        UserDTO userDTO = new UserDTO();
        userDTO.setEmail(user.get().getEmail());
        userDTO.setName(user.get().getName());
        userDTO.setId(user.get().getId());
        userDTO.setUpdatedAt(user.get().getUpdatedAt());
        userDTO.setCreatedAt(user.get().getCreatedAt());
        return userDTO;
    }
}
