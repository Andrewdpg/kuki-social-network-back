package com.kuki.social_networking.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "role", uniqueConstraints = @UniqueConstraint(columnNames = "name"))
@SequenceGenerator(name = "role_seq", sequenceName = "role_seq", initialValue = 2, allocationSize = 1)
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "role_seq")
    private int id;

    @NotBlank(message = "Rol name is mandatory")
    @Pattern(regexp = "^[A-Z]+$", message = "Invalid rol name, must be in uppercase")
    @Column(nullable = false)
    private String name;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "role_permission",
            joinColumns = @JoinColumn(name = "role_id"),
            inverseJoinColumns = @JoinColumn(name = "permission_id"))
    private List<Permission> permissions;

    @ManyToMany(mappedBy = "roles")
    private List<User> users;
}