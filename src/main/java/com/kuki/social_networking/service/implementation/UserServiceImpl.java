package com.kuki.social_networking.service.implementation;

import java.io.IOException;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

import com.kuki.social_networking.model.UserStatus;
import com.kuki.social_networking.util.validator.FieldValidator;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.google.common.io.Files;
import com.kuki.social_networking.config.Path;
import com.kuki.social_networking.exception.CountryNotFoundException;
import com.kuki.social_networking.exception.UserAlreadyExistsException;
import com.kuki.social_networking.model.Country;
import com.kuki.social_networking.model.Role;
import com.kuki.social_networking.model.User;
import com.kuki.social_networking.repository.CountryRepository;
import com.kuki.social_networking.repository.RoleRepository;
import com.kuki.social_networking.repository.UserRepository;
import com.kuki.social_networking.request.RegisterUserRequest;
import com.kuki.social_networking.request.UpdateUserRequest;
import com.kuki.social_networking.service.interfaces.EmailService;
import com.kuki.social_networking.service.interfaces.ImageService;
import com.kuki.social_networking.service.interfaces.UserService;

import lombok.RequiredArgsConstructor;

/**
 * Implementation of the user service.
 */
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;
    private final CountryRepository countryRepository;
    private final ImageService imageService;



    /**
     * Registers a new user in the system.
     * Validates if the email and username are unique.
     * Encrypts the password and sets default values.
     * Sends a verification email to the user.
     *
     * @param request The user data to be registered.
     * @return The registered user with updated information.
     */
    @Override
    public User registerUser(RegisterUserRequest request) {
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new UserAlreadyExistsException("Email is already in use.");
        }

        if (userRepository.findByUsername(request.getUsername()).isPresent()) {
            throw new UserAlreadyExistsException("Username is already taken.");
        }

        Country country =  countryRepository.findById(request.getCountry())
            .orElseThrow(()-> new CountryNotFoundException("Country not found"));

        Role role = roleRepository.findByName("DEFAULT")
                .orElseThrow(() -> new RuntimeException("Role not found"));

        User user = User.builder()
            .registerDate(Timestamp.from(Instant.now()))
            .userStatus(UserStatus.ACTIVE)
            .biography("")
            .lastConnection(Timestamp.from(Instant.now()))
            .isEmailVerified(false)
            .username(request.getUsername())
            .email(request.getEmail())
            .password(passwordEncoder.encode(request.getPassword()))
            .fullName(request.getFullName())
            .country(country)
            .roles(List.of(role))
            .build();

        User savedUser = userRepository.save(user);
        emailService.sendVerificationEmail(savedUser);
        return savedUser;
    }

    /**
     * Updates the profile of an existing user.
     *
     * @param user The authenticated user to be updated.
     * @param request The updated user data.
     * @return The updated user.
     */
    @Override
    public User updateUserProfile(User user, UpdateUserRequest request) {

        if (request.getFullName() != null) {
            user.setFullName(request.getFullName());
        }
        if (request.getBiography() != null) {
            user.setBiography(request.getBiography());
        }
        if (request.getSocialMedia() != null) {
            user.setSocialMedia(request.getSocialMedia());
        }
    
        return userRepository.save(user);
    }

    /**
     * Retrieves a list of users that match the given search criteria.
     *
     * @param search might be username or full name.
     * @return A list of matching users.
     */
    @Override
    public List<User> searchUsers(String search) {
        return userRepository.findByUsernameContainingIgnoreCaseOrFullNameContainingIgnoreCase(search, search);
    }

    /**
     * Deletes a user from the system.
     *
     * @param user The user to be deleted.
     */
    @Override
    public void deleteUser(User user) {
        userRepository.delete(user);
    }

    /**
     * Retrieves a list of all users from the repository.
     *
     * @return a list of all users
     */
    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    /**
     * Retrieves a user by their username.
     *
     * @param username The username of the user to retrieve.
     * @return The user with the specified username.
     */
    @Override
    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    @Override
    public User uploadProfilePicture(User user, MultipartFile file) {
        try {
            imageService.validateImage(file);

            String imageUrl = imageService.uploadImage(file);

            user.setPhotoUrl(imageUrl);
            return userRepository.save(user);
        } catch (IOException e) {
            throw new RuntimeException("Failed to upload profile picture", e);
        }
    }
}
