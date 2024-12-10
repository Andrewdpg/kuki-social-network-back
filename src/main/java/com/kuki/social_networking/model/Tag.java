package com.kuki.social_networking.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * This class represents the tags that the users are allowed to use in their recipes.
 */
@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tag")
public class Tag {

    @Id
    private String name;

    @ManyToMany(mappedBy = "tags")
    private List<Recipe> recipes;
}
