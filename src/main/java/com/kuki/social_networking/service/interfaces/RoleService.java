package com.kuki.social_networking.service.interfaces;

import java.util.List;
import java.util.Optional;

import com.kuki.social_networking.model.Role;
import com.kuki.social_networking.model.User;
import com.kuki.social_networking.request.AssociatePermissionsRequest;
import com.kuki.social_networking.request.AssociateRoleToUserRequest;
import com.kuki.social_networking.request.CreateRoleRequest;

public interface RoleService {

    /**
     * Creates a new role based on the provided request.
     *
     * @param request the request object containing the details for the new role
     * @return the created Role object
     */
    Role createRole(CreateRoleRequest request);

    /**
     * Associates a set of permissions to a specified role based on the provided request.
     *
     * @param request the request object containing the role and the permissions to be associated
     * @return the updated Role object with the associated permissions
     */
    Role associatePermissionsToRole(AssociatePermissionsRequest request);

    /**
     * Associates a role to a user based on the provided request.
     *
     * @param request the request containing the details needed to associate the role to the user
     */
    void associateRoleToUser(AssociateRoleToUserRequest request);

    /**
     * Deletes a role based on the provided role ID.
     *
     * @param roleId the ID of the role to be deleted
     */
    void deleteRole(int roleId);

    /**
     * Removes a role from a user based on the provided request.
     *
     * @param request the request containing the details of the role to be removed from the user
     */
    void removeRoleFromUser(AssociateRoleToUserRequest request);

    /**
     * Finds a role by its name.
     *
     * @param name the name of the role to find
     * @return an Optional containing the found Role, or an empty Optional if no role with the given name is found
     */
    Optional<Role> findByName(String name);

    /**
     * Retrieves a list of all roles.
     *
     * @return a list of Role objects representing all roles.
     */
    List<Role> findAll(); 

    /**
     * Retrieves a Role by its unique identifier.
     *
     * @param id the unique identifier of the Role
     * @return an Optional containing the Role if found, or an empty Optional if not found
     */
    Optional<Role> findById(int id);

    /**
     * Retrieves a list of roles that are not assigned to the specified user.
     *
     * @param user the user for which to retrieve unassigned roles
     * @return a list of Role objects representing the unassigned roles
     */
    List<Role> getUnassignedRoles(User user);
}