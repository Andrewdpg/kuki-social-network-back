package com.kuki.social_networking.model;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.ArrayList;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

@ExtendWith(MockitoExtension.class)
public class RoleTest {

    private Validator validator;

    @Mock
    private Permission mockPermission;

    @Mock
    private User mockUser;

    @BeforeEach
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void testRoleCreation() {
        List<Permission> permissions = new ArrayList<>();
        permissions.add(mockPermission);

        List<User> users = new ArrayList<>();
        users.add(mockUser);

        Role role = new Role();
        role.setId(1);
        role.setName("ADMIN");
        role.setPermissions(permissions);
        role.setUsers(users);

        assertNotNull(role);
        assertEquals(1, role.getId());
        assertEquals("ADMIN", role.getName());
        assertEquals(permissions, role.getPermissions());
        assertEquals(users, role.getUsers());
    }

    @Test
    public void testRoleValidation() {
        Role role = new Role();
        Set<ConstraintViolation<Role>> violations = validator.validate(role);
        assertFalse(violations.isEmpty());

        role.setName("ADMIN");
        violations = validator.validate(role);
        assertTrue(violations.isEmpty());
    }

    @Test
    public void testRoleNameNotBlank() {
        Role role = new Role();
        role.setName("");

        Set<ConstraintViolation<Role>> violations = validator.validate(role);
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals("Rol name is mandatory")));
    }

    @Test
    public void testRoleNamePattern() {
        Role role = new Role();
        role.setName("admin");

        Set<ConstraintViolation<Role>> violations = validator.validate(role);
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals("Invalid rol name, must be in uppercase")));
    }
}