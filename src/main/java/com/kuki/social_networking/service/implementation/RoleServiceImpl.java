package com.kuki.social_networking.service.implementation;

import java.util.List;
import java.util.Optional;

import com.kuki.social_networking.model.User;
import com.kuki.social_networking.repository.UserRepository;
import com.kuki.social_networking.service.interfaces.AuthService;
import com.kuki.social_networking.service.interfaces.UserService;
import org.springframework.stereotype.Service;

import com.kuki.social_networking.exception.RoleNotFoundException;
import com.kuki.social_networking.model.Permission;
import com.kuki.social_networking.model.Role;
import com.kuki.social_networking.repository.RoleRepository;
import com.kuki.social_networking.request.AssociatePermissionsRequest;
import com.kuki.social_networking.request.AssociateRoleToUserRequest;
import com.kuki.social_networking.request.CreateRoleRequest;
import com.kuki.social_networking.service.interfaces.PermissionService;
import com.kuki.social_networking.service.interfaces.RoleService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;
    private final PermissionService permissionService;
    private final UserService userService;
    private final UserRepository userRepository;
    private final AuthService authService;

    /**
     * Creates a new role based on the provided request.
     *
     * @param request the request object containing the details for the new role
     * @return the created Role object after being saved in the repository
     */
    @Override
    public Role createRole(CreateRoleRequest request) {

        request.setName(request.getName().toUpperCase());

        if(roleRepository.findByName(request.getName()).isPresent()) {
            throw new IllegalArgumentException("Role already exists");
        }

        if (request.getName().isEmpty()) {
            throw new IllegalArgumentException("Name cannot be empty");
        }

        if (!request.getName().matches("^[A-Z]+$")) {
            throw new IllegalArgumentException("Name must contain only uppercase letters");
        }

        Role role = new Role();
        role.setName(request.getName());
        return roleRepository.save(role);
    }

    /**
     * Associates a list of permissions to a role.
     *
     * @param request the request containing the role ID and the list of permission IDs to associate
     * @return the updated Role entity with the associated permissions
     * @throws RoleNotFoundException if the role with the specified ID is not found
     */
    @Override
    public Role associatePermissionsToRole(AssociatePermissionsRequest request) {
        Role role = roleRepository.findById(request.getRoleId())
                .orElseThrow(() -> new RoleNotFoundException("Role not found"));
        List<Integer> permissionIds = request.getPermissions();
        List<Permission> permissions = permissionService.findAllById(permissionIds);
        role.setPermissions(permissions);
        return roleRepository.save(role);
    }

    /**
     * Associates a role to a user based on the provided request.
     *
     * @param request the request containing the user and role information
     * @throws RoleNotFoundException if the role with the specified ID is not found
     */
    @Override
    public void associateRoleToUser(AssociateRoleToUserRequest request) {
        Role role = roleRepository.findById(request.getRoleId())
            .orElseThrow(() -> new RoleNotFoundException("Role not found"));

        User user = userService.getUserByUsername(request.getUsername());

        if(user.getRoles().contains(role))
            throw new IllegalArgumentException("User already has this role");

        user.getRoles().add(role);
        userRepository.save(user);
    }

    /**
     * Deletes a role by its ID.
     *
     * @param roleId the ID of the role to be deleted
     */
    @Override
    public void deleteRole(int roleId) {
        if(roleId == 1 || roleId == 0)
            throw new IllegalArgumentException("Cannot delete default roles");

        roleRepository.deleteById(roleId);
    }

    /**
     * Removes a role from a user based on the provided request.
     *
     * @param request the request containing the user and role information
     * @throws RoleNotFoundException if the role specified in the request is not found
     */
    @Override
    public void removeRoleFromUser(AssociateRoleToUserRequest request) {
        Role role = roleRepository.findById(request.getRoleId())
                .orElseThrow(() -> new RoleNotFoundException("Role not found"));

        if(role.getName().equals("DEFAULT"))
            throw new IllegalArgumentException("Cannot remove default role");

        User user = userService.getUserByUsername(request.getUsername());

        if(user.getUsername().equals(authService.getAuthenticatedUser().getUsername())){
            throw new IllegalArgumentException("Cannot update your own roles");
        }

        if(!user.getRoles().contains(role))
            throw new IllegalArgumentException("User does not have this role");

        user.getRoles().remove(role);
        userRepository.save(user);
    }

    /**
     * Finds a role by its name.
     *
     * @param name the name of the role to find
     * @return an Optional containing the found Role, or an empty Optional if no role is found
     */
    @Override
    public Optional<Role> findByName(String name) {
        return roleRepository.findByName(name);
    }

    /**
     * Retrieves all roles from the repository.
     *
     * @return a list of all roles.
     */
    @Override
    public List<Role> findAll() { 
        return roleRepository.findAll();
    }

    /**
     * Finds a role by its ID.
     *
     * @param id the ID of the role to find
     * @return an Optional containing the found role, or an empty Optional if no role is found
     */
    @Override
    public Optional<Role> findById(int id) {
        return roleRepository.findById(id);
    }

    /**
     * Retrieves a list of roles that are not assigned to the specified user.
     *
     * @param user the user for which to retrieve unassigned roles
     * @return a list of Role objects representing the unassigned roles
     */
    @Override
    public List<Role> getUnassignedRoles(User user) {
        return roleRepository.findRolesNotAssignedToUser(user);
    }
}