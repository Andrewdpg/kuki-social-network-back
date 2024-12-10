package com.kuki.social_networking.controller.mvc;

import com.kuki.social_networking.model.Permission;
import com.kuki.social_networking.request.CreatePermissionRequest;
import com.kuki.social_networking.service.interfaces.PermissionService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/admin/permission")
@RequiredArgsConstructor
public class PermissionController {
    private final PermissionService permissionService;

    /**
     * Handles GET requests to the "/create" endpoint and returns the view for creating a new permission.
     * 
     * @param model the model to which the "createPermissionRequest" attribute is added
     * @return the name of the view to be rendered, in this case "create-permission"
     */
    @GetMapping("/create")
    @PreAuthorize("hasAuthority('ADMIN_ROLES')")
    public String showCreatePermissionForm(Model model) {
        model.addAttribute("createPermissionRequest", new CreatePermissionRequest());
        return "create-permission";
    }

    /**
     * Handles the creation of a new permission.
     *
     * @param request the request object containing the details for the new permission
     * @param model the model to which the new permission will be added
     * @return a redirect string to the permissions page
     */
    @PostMapping("/create")
    @PreAuthorize("hasAuthority('ADMIN_ROLES')")
    public String createPermission(@ModelAttribute CreatePermissionRequest request, Model model) {
        Permission permission = permissionService.createPermission(request);
        model.addAttribute("permission", permission);
        return "redirect:/admin/permission";
    }

    /**
     * Handles GET requests to retrieve a permission by its ID.
     *
     * @param id    the ID of the permission to retrieve
     * @param model the model to add attributes to
     * @return the name of the view to render
     */
    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN_ROLES')")
    public String findById(@PathVariable int id, Model model) {
        Optional<Permission> permission = permissionService.findById(id);
        if (permission.isPresent()) {
            model.addAttribute("permission", permission.get());
            return "permission-detail";
        } else {
            return "error/404";
        }
    }

    /**
     * Handles GET requests to retrieve all permissions.
     * 
     * @param model the model to which the list of permissions will be added
     * @return the name of the view to be rendered, which is "permissions-list"
     */
    @GetMapping
    @PreAuthorize("hasAuthority('ADMIN_ROLES')")
    public String findAll(Model model) {
        List<Permission> permissions = permissionService.findAll();
        model.addAttribute("permissions", permissions);
        return "permissions-list";
    }

    /**
     * Handles the HTTP POST request to delete a permission by its ID.
     *
     * @param id the ID of the permission to be deleted
     * @return a redirect string to the permissions page
     */
    @PostMapping("/delete/{id}")
    @PreAuthorize("hasAuthority('ADMIN_ROLES')")
    public String deletePermission(@PathVariable int id) {
        permissionService.deletePermission(id);
        return "redirect:/admin/permission";
    }
}