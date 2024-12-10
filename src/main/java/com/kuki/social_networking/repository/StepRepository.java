package com.kuki.social_networking.repository;

import com.kuki.social_networking.model.Step;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for managing {@link Step} entities.
 * Extends {@link JpaRepository} for basic CRUD operations.
**/
@Repository
public interface StepRepository extends JpaRepository<Step, UUID> {
    Page<Step> findAllByRecipeId(UUID recipeId, Pageable pageable);
}
