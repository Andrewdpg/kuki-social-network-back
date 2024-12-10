package com.kuki.social_networking.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * This class represents the meat planners made by users.
 */
@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "meal_planner")
public class MealPlanner {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @NotBlank(message = "Meal planner name is mandatory")
    @Column(nullable = false)
    private String name;

    @NotBlank(message = "Meal planner description is mandatory")
    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    @Builder.Default
    private Boolean isPublic = false;

    @OneToMany(mappedBy = "mealPlanner")
    private List<MealDay> mealDays;

    @OneToMany(mappedBy = "mealPlanner", cascade = CascadeType.ALL)
    private List<UserMealPlanner> userMealPlanners;
}
