package com.kuki.social_networking.service.interfaces;

import com.kuki.social_networking.model.Permission;
import com.kuki.social_networking.request.CreatePermissionRequest;

import java.util.List;
import java.util.Optional;

public interface PermissionService {

    /**
     * Creates a new permission based on the provided request.
     *
     * @param request the request object containing the details for the new permission
     * @return the created Permission object
     */
    Permission createPermission(CreatePermissionRequest request);

    /**
     * Retrieves a Permission entity by its unique identifier.
     *
     * @param id the unique identifier of the Permission entity
     * @return an Optional containing the Permission entity if found, or an empty Optional if not found
     */
    Optional<Permission> findById(int id);

    /**
     * Retrieves a list of all permissions.
     *
     * @return a list of Permission objects.
     */
    List<Permission> findAll();

    /**
     * Retrieves a list of Permission objects based on the provided list of IDs.
     *
     * @param ids A list of integer IDs for which the corresponding Permission objects are to be retrieved.
     * @return A list of Permission objects that match the provided IDs.
     */
    List<Permission> findAllById(List<Integer> ids);

    /**
     * Deletes the permission with the specified ID.
     *
     * @param id the ID of the permission to be deleted
     */
    void deletePermission(int id);

}