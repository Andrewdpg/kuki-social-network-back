package com.kuki.social_networking.controller.mvc;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.kuki.social_networking.exception.RoleNotFoundException;
import com.kuki.social_networking.model.Permission;
import com.kuki.social_networking.model.Role;
import com.kuki.social_networking.request.AssociatePermissionsRequest;
import com.kuki.social_networking.request.CreateRoleRequest;
import com.kuki.social_networking.service.interfaces.PermissionService;
import com.kuki.social_networking.service.interfaces.RoleService;

import lombok.RequiredArgsConstructor;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin/role")
@RequiredArgsConstructor
public class RoleController {
    private final RoleService roleService;
    private final PermissionService permissionService;

    /**
     * Handles GET requests to retrieve a list of roles.
     *
     * @param model the model to which the list of roles will be added
     * @return the name of the view to be rendered, which is "roles-list"
     */
    @GetMapping
    @PreAuthorize("hasAuthority('ADMIN_ROLES')")
    public String listRoles(Model model) {
        List<Role> roles = roleService.findAll();
        model.addAttribute("roles", roles);
        return "roles-list";
    }

    /**
     * Handles GET requests to the "/create" endpoint.
     * This method prepares the model with a new instance of CreateRoleRequest
     * and returns the view name "create-role" to display the role creation form.
     *
     * @param model the Model object used to pass attributes to the view
     * @return the name of the view to be rendered, "create-role"
     */
    @GetMapping("/create")
    @PreAuthorize("hasAuthority('ADMIN_ROLES')")
    public String showCreateRoleForm(Model model) {
        model.addAttribute("createRoleRequest", new CreateRoleRequest());
        return "create-role";
    }

/**
 * Handles the creation of a new role.
 *
 * @param request the request object containing the details for the new role
 * @param model the model to which the newly created role will be added
 * @return a redirect string to the roles page
 */
@PostMapping("/create")
@PreAuthorize("hasAuthority('ADMIN_ROLES')")
public String createRole(@ModelAttribute CreateRoleRequest request, Model model, RedirectAttributes redirectAttributes) {
    try {
        Role role = roleService.createRole(request);
        model.addAttribute("role", role);
        return "redirect:/admin/role";
    } catch (RoleNotFoundException | IllegalArgumentException e) {
        redirectAttributes.addFlashAttribute("error", e.getMessage());
        return "redirect:/admin/role/create";
    }
}

    /**
     * Handles GET requests to the "/associate-permissions" endpoint.
     * Displays a form for associating permissions with a role.
     *
     * @param roleId the ID of the role to associate permissions with
     * @param model the model to hold attributes for the view
     * @return the name of the view to render
     * @throws RoleNotFoundException if the role with the specified ID is not found
     */
    @GetMapping("/{roleId}")
    @PreAuthorize("hasAuthority('ADMIN_ROLES')")
    public String showAssociatePermissionsForm(@PathVariable("roleId") int roleId, Model model) {
        List<Role> roles = roleService.findAll();
        List<Permission> permissions = permissionService.findAll();
        Role role = roleService.findById(roleId).orElseThrow(() -> new RoleNotFoundException("Role not found"));
        AssociatePermissionsRequest associatePermissionsRequest = new AssociatePermissionsRequest();
        associatePermissionsRequest.setRoleId(roleId);
        associatePermissionsRequest.setPermissions(role.getPermissions().stream().map(Permission::getId).collect(Collectors.toList()));

        model.addAttribute("roles", roles);
        model.addAttribute("permissions", permissions);
        model.addAttribute("associatePermissionsRequest", associatePermissionsRequest);
        return "associate-permissions";
    }

    /**
     * Associates permissions to a role based on the provided request.
     *
     * @param request the request object containing the role and permissions to be associated
     * @return a redirect string to the roles page
     */
    @PostMapping("/{roleId}")
    @PreAuthorize("hasAuthority('ADMIN_ROLES')")
    public String associatePermissionsToRole(@ModelAttribute AssociatePermissionsRequest request) {
        roleService.associatePermissionsToRole(request);
        return "redirect:/admin/role";
    }

    /**
     * Handles the deletion of a role by its ID.
     *
     * @param roleId the ID of the role to be deleted
     * @return a redirect to the roles page
     */
    @DeleteMapping("/{roleId}")
    @PreAuthorize("hasAuthority('ADMIN_ROLES')")
    public String deleteRole(@PathVariable int roleId, RedirectAttributes redirectAttributes) {
        try {
            roleService.deleteRole(roleId);
            return "redirect:/admin/role";
        } catch (RoleNotFoundException | IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/admin/role";
        }
    }
}