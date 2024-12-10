package com.kuki.social_networking.request;

import com.kuki.social_networking.model.User;

import lombok.Data;

/**
 * Represents a request to associate a role with a user.
 */
@Data
public class AssociateRoleToUserRequest {
    private int roleId;
    private String username;
}