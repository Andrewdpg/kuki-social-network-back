package com.kuki.social_networking.repository;

import java.util.List;
import java.util.Optional;

import com.kuki.social_networking.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import com.kuki.social_networking.model.Role;
import org.springframework.data.jpa.repository.Query;

/**
 * Repository for Role entities.
 */
public interface RoleRepository extends JpaRepository<Role, Integer> {
    /**
     * Finds a role by its name.
     *
     * @param name the name of the role to find
     * @return an Optional containing the found Role, or an empty Optional if no role with the given name is found
     */
    Optional<Role> findByName(String name);

    /**
     * Retrieves a list of all roles that are not assigned to the specified user.
     *
     * @param user the user for which to find unassigned roles
     * @return a list of roles that are not assigned to the user
     */
    @Query("SELECT r FROM Role r WHERE r NOT IN (SELECT u.roles FROM User u WHERE u = :user)")
    List<Role> findRolesNotAssignedToUser(User user);
}
