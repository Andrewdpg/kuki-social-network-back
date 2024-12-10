package com.kuki.social_networking.model;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "permission")
@SequenceGenerator(name = "permission_seq", sequenceName = "permission_seq", initialValue = 2, allocationSize = 1)
public class Permission {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "permission_seq")
    private int id;

    @NotBlank(message = "Permission name is mandatory")
    @Pattern(regexp = "^[A-Z]+_[A-Z_]+$", message = "Invalid permission name, must be in uppercase and separated by underscores")
    @Column(nullable = false, unique = true)
    private String name;

    @ManyToMany(mappedBy = "permissions")
    private List<Role> roles;
}