package com.kuki.social_networking.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import java.sql.Timestamp;
import java.time.Duration;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * This class represents the recipes published by the users.
 */
@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "recipe", indexes = {
        @Index(name = "recipe_owner_index", columnList = "recipe_owner"),
        @Index(name = "recipe_title_index", columnList = "title"),
        @Index(name = "recipe_country_index", columnList = "country_code"),
        @Index(name = "recipe_estimated_time_index", columnList = "estimatedTime")
})
public class Recipe {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @NotBlank(message = "Recipe title is mandatory")
    @Column(nullable = false)
    private String title;

    @NotBlank(message = "Recipe description is mandatory")
    @Column(nullable = false)
    private String description;

    @NotBlank(message = "Recipe photo is mandatory")
    @Column(nullable = false)
    private String photoUrl;

    @Column
    private Timestamp publishDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RecipeDifficulty difficulty;

    @ManyToOne(optional = false)
    private Country country;

    @Column
    private Duration estimatedTime;

    @OneToMany(mappedBy = "recipe", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Step> steps;

    @OneToMany(mappedBy = "recipe", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments;

    @ManyToMany(mappedBy = "recipes")
    private List<MealDay> mealDays;

    @ManyToOne(optional = false)
    @JoinColumn(name = "recipe_owner", nullable = false)
    private User recipeOwner;

    @OneToMany(mappedBy = "recipe", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RecipeIngredient> ingredients;

    @ManyToMany(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinTable(
            name = "recipe_tags",
            joinColumns = @JoinColumn(name = "recipe_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id"),
            uniqueConstraints = @UniqueConstraint(columnNames = {"recipe_id", "tag_id"})
    )
    private List<Tag> tags;

    @ManyToMany(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinTable(
            name = "recipe_likes",
            joinColumns = @JoinColumn(name = "recipe_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"),
            uniqueConstraints = @UniqueConstraint(columnNames = {"recipe_id", "user_id"})
    )
    private List<User> likes;

    @ManyToMany(mappedBy = "savedRecipes")
    private List<User> usersWhoSaved;

    public int getLikesCount() {
        return likes != null ? likes.size() : 0;
    }

    public int getCommentsCount() {
        return comments != null ? comments.size() : 0;
    }
}