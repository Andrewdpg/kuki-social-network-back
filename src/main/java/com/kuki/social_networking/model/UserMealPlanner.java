package com.kuki.social_networking.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * This class represents the relation between a user and a meal planner.
 */
@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "user_meal_planner", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"user_id", "meal_planner_id"})
})
public class UserMealPlanner {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "meal_planner_id", nullable = false)
    private MealPlanner mealPlanner;

    @Column(nullable = false)
    private boolean isOwner;
}
