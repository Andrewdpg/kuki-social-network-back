package com.kuki.social_networking.model;

import com.kuki.social_networking.util.converter.MapToJsonConverter;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * This class represents an ingredient that can be used in recipes.
 */
@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "ingredient", indexes = {
    @Index(name = "ingredient_name_index", columnList = "name")
})
public class Ingredient {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @NotBlank(message = "Ingredient name is mandatory")
    @Column(nullable = false)
    private String name;

    @NotBlank(message = "Ingredient photo is mandatory")
    @Column(nullable = false)
    private String photoUrl;

    @Column
    @Convert(converter = MapToJsonConverter.class)
    @Builder.Default
    private Map<String, Object> nutritionalInfo = new HashMap<>();

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private IngredientUnit unit;

    @OneToMany(mappedBy = "ingredient", orphanRemoval = true)
    private List<RecipeIngredient> recipes;

}