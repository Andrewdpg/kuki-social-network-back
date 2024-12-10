package com.kuki.social_networking.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

/**
 * This class represents the countries that the users are allowed to use in their profiles.
 */
@Data
@Entity
@Table(name = "country")
public class Country {

    @Id
    private String code;

    @Column(nullable = false)
    private String name;
}
