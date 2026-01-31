package com.openclassrooms.mddapi.service;

import com.openclassrooms.mddapi.dto.in.UserCreateDTO;
import com.openclassrooms.mddapi.dto.in.auth.UserAuthDTO;
import com.openclassrooms.mddapi.dto.out.UserDTO;
import com.openclassrooms.mddapi.dto.out.auth.TokenDTO;
import com.openclassrooms.mddapi.entity.RefreshTokenEntity;
import com.openclassrooms.mddapi.entity.UserEntity;
import com.openclassrooms.mddapi.feature.JsonWebToken;
import com.openclassrooms.mddapi.repository.RefreshTokenRepository;
import com.openclassrooms.mddapi.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDate;
import java.util.Optional;

@Service
public class AuthService {
    // 15 min en ms
    long ACCESS_TOKEN_EXPIRATION = 15 * 60 * 1000;
    // Refresh token : 14 jours
    long REFRESH_TOKEN_EXPIRATION = 14 * 24 * 60 * 60 * 1000;
    // Email verification : 1 heure
    long EMAIL_VERIFICATION_EXPIRATION = 60 * 60 * 1000;

    private final UserRepository userRepo;
    private final PasswordEncoder passwordEncoder;
    private final RefreshTokenRepository refreshTokenRepo;
    private final JsonWebToken jsonWebToken;

    public AuthService(UserRepository userRepo, PasswordEncoder passwordEncoder, RefreshTokenRepository refreshTokenRepo, JsonWebToken jsonWebToken) {
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
        this.refreshTokenRepo = refreshTokenRepo;
        this.jsonWebToken = jsonWebToken;
    }

    public TokenDTO register(UserCreateDTO userAuth) {
        Optional<UserEntity> user = userRepo.findByEmail(userAuth.getEmail());
        if (user.isPresent()) {
            throw new RuntimeException("Utilisateur déjà existant");
        }

        TokenDTO dto = new TokenDTO(
                jsonWebToken.generateToken(userAuth.getEmail() , ACCESS_TOKEN_EXPIRATION),
                jsonWebToken.generateToken(userAuth.getEmail() , REFRESH_TOKEN_EXPIRATION)
        );

        userAuth.setEmail(userAuth.getEmail());
        userAuth.setName(userAuth.getName());
        userAuth.setPassword(passwordEncoder.encode(userAuth.getPassword()));

        UserEntity userEntity = new UserEntity();
        userEntity.setEmail(userAuth.getEmail());
        userEntity.setName(userAuth.getName());
        userEntity.setPassword(userAuth.getPassword());
        userEntity.setUpdatedAt(LocalDate.now().toString());
        userEntity.setCreatedAt(LocalDate.now().toString());

        userRepo.save(userEntity);

        RefreshTokenEntity refreshToken = new RefreshTokenEntity();
        refreshToken.setUser(userEntity);
        refreshToken.setToken(dto.getRefresh_token());
        refreshToken.setExpirationDate(Instant.now().plusMillis(REFRESH_TOKEN_EXPIRATION));

        refreshTokenRepo.save(refreshToken);

        return dto;
    }


    public TokenDTO login(UserAuthDTO userAuth) {
        Optional<UserEntity> user = userRepo.findByEmail(userAuth.getEmail());
        if (user.isEmpty()) {
            throw new RuntimeException("Utilisateur inconnu");
        }
        if (!passwordEncoder.matches(userAuth.getPassword(), user.get().getPassword())) {
            throw new RuntimeException("Mot de passe incorrect");
        }

        String accessToken = jsonWebToken.generateToken(user.get().getEmail(), ACCESS_TOKEN_EXPIRATION);
        String refreshTokenStr = jsonWebToken.generateToken(user.get().getEmail(), REFRESH_TOKEN_EXPIRATION);

        RefreshTokenEntity refreshToken = new RefreshTokenEntity();
        refreshToken.setUser(user.get());
        refreshToken.setToken(refreshTokenStr);
        refreshToken.setExpirationDate(Instant.now().plusMillis(REFRESH_TOKEN_EXPIRATION));
        refreshTokenRepo.save(refreshToken);

        return new TokenDTO(accessToken, refreshTokenStr);
    }

    public UserDTO me(String email) {
        Optional<UserEntity> user = userRepo.findByEmail(email);
        if (user.isEmpty()) {
            throw new RuntimeException("Utilisateur inconnu");
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
