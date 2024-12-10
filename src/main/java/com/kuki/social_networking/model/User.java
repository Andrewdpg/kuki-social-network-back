package com.kuki.social_networking.model;

import com.kuki.social_networking.util.converter.MapToJsonConverter;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.*;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * This class represents the users.
 */
@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users", indexes = {
    @Index(name = "user_email_index", columnList = "email"),
    @Index(name = "user_full_name_index", columnList = "full_name"),
    @Index(name = "user_country_index", columnList = "country_code"),
}, uniqueConstraints = {
    @UniqueConstraint(columnNames = {"email"})
})
public class User {

    @Id
    private String username;

    @Email(message = "El correo electrónico debe ser válido")
    @NotBlank(message = "User email is mandatory")
    @Column(nullable = false, unique = true)
    private String email;

    @NotBlank(message = "User name is mandatory")
    @Column(nullable = false)
    private String fullName;

    @NotBlank(message = "User password is mandatory")
    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    @Builder.Default
    private String photoUrl = "https://i.imgur.com/NJ1ZVAh.jpeg";

    @Size(max = 500, message = "The biography cannot exceed 500 characters")
    @Builder.Default
    private String biography = "";

    @Column
    @Convert(converter = MapToJsonConverter.class)
    @Builder.Default
    private Map<String, Object> socialMedia = new HashMap<>();

    @Column(nullable = false)
    private Timestamp registerDate;

    @Column(nullable = false)
    @Builder.Default
    private UserStatus userStatus = UserStatus.ACTIVE;

    @ManyToOne(optional = false)
    private Country country;

    private Timestamp lastConnection;

    // TODO: agregar .default
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_role",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private List<Role> roles;

    @OneToMany(mappedBy = "follower")
    private List<UserFollow> following;

    @OneToMany(mappedBy = "followed")
    private List<UserFollow> followers;

    @OneToMany(mappedBy = "notificationOwner")
    private List<Notification> notifications;

    @OneToMany(mappedBy = "recipeOwner")
    private List<Recipe> recipes;

    @OneToMany(mappedBy = "commentOwner")
    private List<Comment> comments;

    @ManyToMany(mappedBy = "likes")
    private List<Recipe> likes;

    @Column(nullable = false)
    @Builder.Default
    private boolean isEmailVerified = false;

    @ManyToMany
    @JoinTable(
            name = "user_meal_planner",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "meal_planner_id")
    )
    private List<UserMealPlanner> mealPlanners;

    @ManyToMany
    @JoinTable(
            name = "saved_recipes",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "recipe_id"),
            uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "recipe_id"})
    )
    private List<Recipe> savedRecipes;

}
