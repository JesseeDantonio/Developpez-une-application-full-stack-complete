package com.openclassrooms.mddapi.repository;

import com.openclassrooms.mddapi.entity.RefreshTokenEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RefreshTokenRepository extends JpaRepository<RefreshTokenEntity, Integer> {
}
