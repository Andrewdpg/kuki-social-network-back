package com.kuki.social_networking.controller.mvc;

import java.util.List;

import com.kuki.social_networking.exception.RoleNotFoundException;
import com.kuki.social_networking.model.Role;
import com.kuki.social_networking.request.AssociateRoleToUserRequest;
import com.kuki.social_networking.service.interfaces.RoleService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;

import com.kuki.social_networking.model.User;
import com.kuki.social_networking.service.interfaces.UserService;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/user")
public class AdminUserController {

    private final UserService userService;
    private final RoleService roleService;

    /**
     * Handles GET requests to the "/users" endpoint.
     * Retrieves a list of all users from the user service,
     * adds the list to the model, and returns the view name "users-list".
     *
     * @param model the model to which the list of users will be added
     * @return the name of the view to be rendered, "users-list"
     */
    @GetMapping
    @PreAuthorize("hasAuthority('ADMIN_USERS')")
    public String listUsers(Model model) {
        List<User> users = userService.getAllUsers();
        model.addAttribute("users", users);
        return "users-list";
    }

    /**
     * Handles GET requests to the "/admin/user/{username}" endpoint.
     * Retrieves a specific user by username from the user service,
     * adds the user to the model, and returns the view name "user".
     *
     * @param username the username of the user to retrieve
     * @param model the model to which the user will be added
     * @return the name of the view to be rendered, "user"
     */
    @GetMapping("/{username}")
    @PreAuthorize("hasAuthority('ADMIN_USERS')")
    public String showUserProfile(@PathVariable String username, Model model) {

        User user = userService.getUserByUsername(username);
        List<Role> unassignedRoles = roleService.getUnassignedRoles(user);

        AssociateRoleToUserRequest request = new AssociateRoleToUserRequest();
        request.setUsername(username);

        model.addAttribute("user", user);
        model.addAttribute("unassignedRoles", unassignedRoles);
        model.addAttribute("associateRoleToUserRequest", request);
        return "user";
    }

    /**
     * Associates a role to a user based on the provided request.
     *
     * @param request the request object containing user and role information
     * @return a redirect string to the roles page
     */
    @PostMapping("/{username}/role")
    @PreAuthorize("hasAuthority('ADMIN_USERS_ROLES')")
    public String associateRoleToUser(@ModelAttribute AssociateRoleToUserRequest request, RedirectAttributes redirectAttributes) {
        try {
            roleService.associateRoleToUser(request);
            return "redirect:/admin/user";
        } catch (RoleNotFoundException | IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/admin/user/" + request.getUsername();
        }
    }

    /**
     * Handles the POST request to remove a role from a user.
     *
     * @param request the request object containing the details of the role to be removed from the user
     * @return a redirect string to the roles page
     */
    @DeleteMapping("/{username}/role")
    @PreAuthorize("hasAuthority('ADMIN_USERS_ROLES')")
    public String removeRoleFromUser(@ModelAttribute AssociateRoleToUserRequest request, RedirectAttributes redirectAttributes) {
        try {
            roleService.removeRoleFromUser(request);
            return "redirect:/admin/user";
        } catch (RoleNotFoundException | IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/admin/user/" + request.getUsername();
        }
    }
}