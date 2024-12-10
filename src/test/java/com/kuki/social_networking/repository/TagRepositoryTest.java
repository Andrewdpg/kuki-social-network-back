package com.kuki.social_networking.repository;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.sql.Timestamp;
import java.time.Duration;
import java.time.Instant;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.kuki.social_networking.model.Recipe;
import com.kuki.social_networking.model.RecipeDifficulty;
import com.kuki.social_networking.model.Tag;
import com.kuki.social_networking.model.User;
import com.kuki.social_networking.model.Role;
import com.kuki.social_networking.model.UserStatus;

// TODO: Remake test
@Disabled
@SpringBootTest
public class TagRepositoryTest {

    @Autowired
    private TagRepository tagRepository;

    @Autowired
    private RecipeRepository recipeRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    private final List<Role> userRoles = List.of(
            roleRepository.getReferenceById(1)
    );

    private Recipe recipe;
    private User user;

    /**
     * Sets up the test environment before each test.
     * Creates a test user and a test recipe, and saves them to the database.
     */
    @BeforeEach
    public void setUp() {
        user = User.builder()
            .username("test_user")
            .email("test_user@example.com")
            .fullName("Test User")
            .password("password")
            .registerDate(Timestamp.from(Instant.now()))
            .userStatus(UserStatus.ACTIVE)
            .country("Country")
            .roles(userRoles)
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
     * Tests the creation of a new tag.
     * Verifies that:
     * 1. The tag is successfully saved
     * 2. The saved tag is not null
     * 3. The name of the saved tag matches the input
     */
    @Test
    @Transactional
    public void testCreateTag() {
        Tag tag = Tag.builder()
                .name("Vegetarian")
                .recipes(new ArrayList<>())
                .build();
        tag = tagRepository.save(tag);

        assertNotNull(tag);
        assertEquals("Vegetarian", tag.getName());
    }

    /**
     * Tests finding a tag by its name.
     * Verifies that:
     * 1. A tag can be successfully retrieved by its name
     * 2. The retrieved tag has the correct name
     */
    @Test
    @Transactional
    public void testFindTagByName() {
        Tag tag = Tag.builder()
                .name("Vegan")
                .recipes(new ArrayList<>())
                .build();
        tagRepository.save(tag);

        Optional<Tag> foundTag = tagRepository.findById("Vegan");
        assertTrue(foundTag.isPresent());
        assertEquals("Vegan", foundTag.get().getName());
    }

    /**
     * Tests the deletion of a tag.
     * Verifies that:
     * 1. A tag can be successfully deleted
     * 2. The deleted tag cannot be found in the database after deletion
     */
    @Test
    @Transactional
    public void testDeleteTag() {
        Tag tag = Tag.builder()
                .name("Dairy-Free")
                .recipes(new ArrayList<>())
                .build();
        tag = tagRepository.save(tag);

        tagRepository.delete(tag);

        Optional<Tag> deletedTag = tagRepository.findById("Dairy-Free");
        assertTrue(deletedTag.isEmpty());
    }
}