package com.kuki.social_networking.service.implementation;

import com.kuki.social_networking.exception.PermissionNotFoundException;
import com.kuki.social_networking.model.Permission;
import com.kuki.social_networking.repository.PermissionRepository;
import com.kuki.social_networking.request.CreatePermissionRequest;
import com.kuki.social_networking.service.interfaces.PermissionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
* Service implementation for managing permissions.
*/
@Service
@RequiredArgsConstructor
public class PermissionServiceImpl implements PermissionService {
    private final PermissionRepository permissionRepository;

    /**
     * Creates a new permission based on the provided request.
     *
     * @param request the request containing the details for the new permission
     * @return the created Permission object
     */
    @Override
    public Permission createPermission(CreatePermissionRequest request) {
        Permission permission = new Permission();
        permission.setName(request.getName());
        return permissionRepository.save(permission);
    }

    /**
     * Finds a Permission by its ID.
     *
     * @param id the ID of the Permission to find
     * @return an Optional containing the found Permission, or an empty Optional if no Permission is found
     */
    @Override
    public Optional<Permission> findById(int id) {
        return permissionRepository.findById(id);
    }

    /**
     * Retrieves all permissions from the repository.
     *
     * @return a list of all permissions.
     */
    @Override
    public List<Permission> findAll() {
        return permissionRepository.findAll();
    }

    /**
     * Retrieves a list of permissions based on their IDs.
     *
     * @param ids a list of permission IDs to retrieve
     * @return a list of permissions corresponding to the provided IDs
     */
    @Override
    public List<Permission> findAllById(List<Integer> ids) {
        return permissionRepository.findAllById(ids);
    }


    /**
     * Deletes a permission by its ID.
     *
     * @param id the ID of the permission to be deleted
     * @throws PermissionNotFoundException if the permission with the specified ID is not found
     */
    @Override
    public void deletePermission(int id) {
        Permission permission = permissionRepository.findById(id)
                .orElseThrow(() -> new PermissionNotFoundException("Permission not found"));
        permissionRepository.delete(permission);
    }
}