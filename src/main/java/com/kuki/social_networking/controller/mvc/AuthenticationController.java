package com.kuki.social_networking.controller.mvc;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.kuki.social_networking.service.interfaces.AuthService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthService authService;

    /**
     * Handles GET requests to the "/login" endpoint.
     * 
     * @return The name of the view to be rendered, in this case, "login".
     */
    @GetMapping("/login")
    public String login() {
        return "login";
    }

    /**
     * Handles the login request for a user.
     *
     * @param username the username of the user attempting to log in
     * @param password the password of the user attempting to log in
     * @param model the model to add attributes to be used in the view
     * @return a redirect to the users page if authentication is successful, 
     *         otherwise returns the login page with an error message
     */
    @PostMapping("/login")
    public String loginUser(@RequestParam String username, @RequestParam String password, Model model) {
        try {
            authService.authenticateUser(username, password);
            return "redirect:/home";
        } catch (Exception e) {
            model.addAttribute("loginError", "Nombre de usuario o contraseña incorrectos");
            return "login";
        }
    }

    /**
     * Handles GET requests to the "/logout" endpoint.
     * This method logs out the currently authenticated user and redirects to the login page.
     *
     * @return a redirect to the login page
     */
    @GetMapping("/logout")
    public String logout() {
        authService.logout();
        return "redirect:/login";
    }
}