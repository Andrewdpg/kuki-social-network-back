package com.kuki.social_networking.model;


import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * This class represents a comment made by a user on a recipe.
 */
@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "comment", indexes = {
    @Index(name = "comment_recipe_id_index", columnList = "recipe_id"),
    @Index(name = "comment_user_id_index", columnList = "user_id"),
    @Index(name = "comment_creation_date_index", columnList = "creation_date")
})
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @NotBlank(message = "Comment content is mandatory")
    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private Timestamp creationDate;

    @ManyToOne(optional = false)
    @JoinColumn(name = "recipe_id", nullable = false)
    private Recipe recipe;

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User commentOwner;

    @OneToMany(mappedBy = "parentComment", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> subComments;

    @ManyToOne
    @JoinColumn(name = "parent_comment_id")
    private Comment parentComment;
}
