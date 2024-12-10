package com.kuki.social_networking.request;

import java.util.List;

import lombok.Data;

/**
 * Represents a request to associate permissions with a specific role.
 * This class contains the role ID and a list of permission IDs to be associated with that role.
 */
@Data
public class AssociatePermissionsRequest {
    private int roleId;
    private List<Integer> permissions;
}
