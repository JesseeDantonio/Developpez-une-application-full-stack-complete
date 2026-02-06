package com.openclassrooms.mddapi.repository;

import com.openclassrooms.mddapi.entity.ThemeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ThemeRepository extends JpaRepository<ThemeEntity, Integer> {
}
