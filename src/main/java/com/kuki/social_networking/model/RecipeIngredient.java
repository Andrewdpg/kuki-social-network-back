package com.kuki.social_networking.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.Min;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * This class represents a relation between a recipe and an ingredient, including its amount.
 */
@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "recipe_ingredient", indexes = {
    @Index(name = "recipe_ingredient_recipe_id_index", columnList = "recipe_id"),
    @Index(name = "recipe_ingredient_ingredient_id_index", columnList = "ingredient_id")
}, uniqueConstraints = {
    @UniqueConstraint(columnNames = {"recipe_id", "ingredient_id"})
})
public class RecipeIngredient {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "recipe_id", nullable = false)
    private Recipe recipe;

    @ManyToOne(optional = false)
    @JoinColumn(name = "ingredient_id", nullable = false)
    private Ingredient ingredient;

    @Min(0)
    @Column(nullable = false)
    private float quantity;

}

