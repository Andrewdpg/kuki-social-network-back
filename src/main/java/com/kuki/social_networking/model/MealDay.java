package com.kuki.social_networking.model;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * This class represents the meal day for a meal planner.
 */
@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "meal_day", indexes = {
    @Index(name = "meal_day_planner_id_index", columnList = "planner_id")
})
public class MealDay {

    @EmbeddedId
    private MealDayId id;

    @NotBlank(message = "Meal day description is mandatory")
    @Column(nullable = false)
    private String description;

    @ManyToOne(optional = false)
    @JoinColumn(name = "planner_id", nullable = false, updatable = false, insertable = false)
    @MapsId("plannerId")
    private MealPlanner mealPlanner;

    @ManyToMany
    @JoinTable(
        name = "meal_day_recipes",
        joinColumns = {
            @JoinColumn(name = "day", referencedColumnName = "day"),
            @JoinColumn(name = "planner_id", referencedColumnName = "planner_id")
        },
        inverseJoinColumns = @JoinColumn(name = "recipe_id")
    )
    private List<Recipe> recipes;

}