package com.kuki.social_networking.model;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
class PermissionTest {

    private Validator validator;

    @BeforeEach
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    /**
     * Test the creation of a Permission object and verifies that all fields are correctly set.
     */
    @Test
    public void testPermissionCreation() {
        Permission permission = new Permission();
        permission.setId(1);
        permission.setName("READ_PRIVILEGES");

        assertNotNull(permission);
        assertEquals(1, permission.getId());
        assertEquals("READ_PRIVILEGES", permission.getName());
    }

    /**
     * Validates a Permission object with missing mandatory fields, expecting validation errors.
     * Then, corrects the fields and revalidates, expecting no validation errors.
     */
    @Test
    public void testPermissionValidation() {
        Permission permission = new Permission();
        Set<ConstraintViolation<Permission>> violations = validator.validate(permission);
        assertFalse(violations.isEmpty());

        permission.setName("READ_PRIVILEGES");
        violations = validator.validate(permission);
        assertTrue(violations.isEmpty());
    }

    /**
     * Validates a Permission object with a blank name, expecting a validation error.
     */
    @Test
    public void testPermissionNameNotBlank() {
        Permission permission = new Permission();
        permission.setName("");

        Set<ConstraintViolation<Permission>> violations = validator.validate(permission);
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals("Permission name is mandatory")));
    }

    /**
     * Validates a Permission object with an invalid name, expecting a validation error.
     */
    @Test
    public void testPermissionNameInvalidPattern() {
        Permission permission = new Permission();
        permission.setName("invalid_name");

        Set<ConstraintViolation<Permission>> violations = validator.validate(permission);
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals("Invalid permission name, must be in uppercase and separated by underscores")));
    }

    /**
     * Validates a Permission object with a valid name, expecting no validation errors.
     */
    @Test
    public void testPermissionNameValidPattern() {
        Permission permission = new Permission();
        permission.setName("VALID_NAME");

        Set<ConstraintViolation<Permission>> violations = validator.validate(permission);
        assertTrue(violations.isEmpty());
    }
}
