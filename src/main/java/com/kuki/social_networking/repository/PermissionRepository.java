package com.kuki.social_networking.repository;

import com.kuki.social_networking.model.Permission;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository interface for managing {@link Permission} entities.
 * Extends the {@link JpaRepository} interface to provide CRUD operations.
 */
public interface PermissionRepository extends JpaRepository<Permission, Integer> {
}