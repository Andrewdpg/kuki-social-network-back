package com.kuki.social_networking.repository;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Timestamp;
import java.time.Duration;
import java.time.Instant;
import java.util.List;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.kuki.social_networking.model.*;
import org.springframework.boot.test.context.SpringBootTest;

// TODO: Remake test
@Disabled
@SpringBootTest
class CommentRepositoryTest {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private RecipeRepository recipeRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    private Recipe recipe;
    private User user;

    @BeforeEach
    void setUp() {
        Role role = roleRepository.getReferenceById(1);

        user = User.builder()
            .username("test_user")
            .email("test_user@example.com")
            .fullName("Test User")
            .password("password")
            .registerDate(Timestamp.from(Instant.now()))
            .userStatus(UserStatus.ACTIVE)
            .country("Country")
            .roles(List.of(role))
            .build();
        user = userRepository.save(user);

        recipe = Recipe.builder()
            .title("Test Recipe")
            .description("Test Description")
            .photoUrl("http://example.com/photo.jpg")
            .publishDate(Timestamp.from(Instant.now()))
            .difficulty(RecipeDifficulty.BASIC)
            .country("Country")
            .estimatedTime(Duration.ofMinutes(30))
            .recipeOwner(user)
            .build();
        recipeRepository.save(recipe);
    }

    /**
     * Tests the ability to save a new comment and then retrieve it by its ID.
     * This test ensures that:
     * 1. A comment can be successfully saved to the database.
     * 2. The saved comment can be retrieved using its ID.
     * 3. The retrieved comment has the correct content.
     */
    @Test
    @Transactional
    void testSaveAndFindCommentById() {
        Comment comment = Comment.builder()
            .content("This is a test comment")
            .creationDate(Timestamp.from(Instant.now()))
            .recipe(recipe)
            .commentOwner(user)
            .build();
        comment = commentRepository.save(comment);

        Comment foundComment = commentRepository.findById(comment.getId()).orElse(null);
        assertNotNull(foundComment);
        assertEquals(comment.getContent(), foundComment.getContent());
    }

    /**
     * Tests the retrieval of comments for a specific recipe, ordered by creation date in descending order.
     * This test verifies that:
     * 1. Multiple comments can be saved for a single recipe.
     * 2. Comments can be retrieved for a specific recipe.
     * 3. The retrieved comments are ordered by creation date in descending order (newest first).
     */
    @Test
    @Transactional
    void testFindByRecipeIdOrderByCreationDateDesc() {
        Comment comment1 = Comment.builder()
            .content("Comment 1")
            .creationDate(Timestamp.from(Instant.now()))
            .recipe(recipe)
            .commentOwner(user)
            .build();
        commentRepository.save(comment1);

        Comment comment2 = Comment.builder()
            .content("Comment 2")
            .creationDate(Timestamp.from(Instant.now().plusSeconds(10)))
            .recipe(recipe)
            .commentOwner(user)
            .build();
        commentRepository.save(comment2);

        List<Comment> comments = commentRepository.findByRecipe_IdOrderByCreationDateDesc(recipe.getId());
        assertEquals(2, comments.size());
        assertEquals("Comment 2", comments.get(0).getContent());
        assertEquals("Comment 1", comments.get(1).getContent());
    }

    /**
     * Tests the deletion of a comment from the database.
     * This test ensures that:
     * 1. A comment can be successfully saved to the database.
     * 2. The saved comment can be deleted using its ID.
     * 3. After deletion, the comment no longer exists in the database.
     */
    @Test
    @Transactional
    void testDeleteComment() {
        Comment comment = Comment.builder()
            .content("Comment to delete")
            .creationDate(Timestamp.from(Instant.now()))
            .recipe(recipe)
            .commentOwner(user)
            .build();
        comment = commentRepository.save(comment);

        commentRepository.deleteById(comment.getId());

        assertFalse(commentRepository.findById(comment.getId()).isPresent());
    }

    /**
     * Tests the retrieval of all comments made by a specific user.
     * This test verifies that:
     * 1. A comment can be successfully saved for a user.
     * 2. All comments made by a specific user can be retrieved.
     * 3. The retrieved comments contain the expected content.
     */
    @Test
    @Transactional
    void testFindAllCommentsByUser() {
        Comment comment = Comment.builder()
            .content("User's comment")
            .creationDate(Timestamp.from(Instant.now()))
            .recipe(recipe)
            .commentOwner(user)
            .build();
        commentRepository.save(comment);

        List<Comment> comments = commentRepository.findAllByCommentOwner(user);
        assertEquals(1, comments.size());
        assertEquals("User's comment", comments.get(0).getContent());
    }
}