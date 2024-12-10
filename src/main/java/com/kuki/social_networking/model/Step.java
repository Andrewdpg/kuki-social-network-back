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
import jakarta.validation.constraints.NotBlank;

import java.time.Duration;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * This class represents the steps that make up a recipe.
 */
@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "step", indexes = {
    @Index(name = "step_recipe_id_index", columnList = "recipe_id")
}, uniqueConstraints = {
    @UniqueConstraint(columnNames = {"recipe_id", "number"})
})
public class Step {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Min(0)
    @Column(nullable = false)
    private int number;

    @NotBlank(message = "Step description is mandatory")
    @Column(nullable = false, length = 165)
    private String description;

    @Column
    private String multimediaUrl;

    @ManyToOne(optional = false)
    @JoinColumn(name = "recipe_id", nullable = false)
    private Recipe recipe;

    @Column
    private Duration estimatedTime;
}
