package com.kuki.social_networking.model;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class CommentTest {

    @Mock
    private User mockUser;

    @Mock
    private Recipe mockRecipe;

    @Mock
    private Comment comment;

    private Validator validator;

    @BeforeEach
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();

        comment = Comment.builder()
                .id(UUID.randomUUID())
                .content("Test comment")
                .creationDate(Timestamp.from(Instant.now()))
                .recipe(mockRecipe)
                .commentOwner(mockUser)
                .build();
    }

    @Test
    public void testCommentCreation() {
        assertNotNull(comment);
        assertNotNull(comment.getId());
        assertEquals("Test comment", comment.getContent());
        assertNotNull(comment.getCreationDate());
        assertEquals(mockRecipe, comment.getRecipe());
        assertEquals(mockUser, comment.getCommentOwner());
    }

    @Test
    public void testCommentValidation() {
        Comment invalidComment = new Comment();
        Set<ConstraintViolation<Comment>> violations = validator.validate(invalidComment);
        assertFalse(violations.isEmpty());

        invalidComment.setContent("Valid content");
        violations = validator.validate(invalidComment);
        assertTrue(violations.isEmpty());
    }

    @Test
    public void testCommentContentNotBlank() {
        Comment invalidComment = Comment.builder()
                .content("")
                .build();

        Set<ConstraintViolation<Comment>> violations = validator.validate(invalidComment);
        assertFalse(violations.isEmpty());
        assertEquals("Comment content is mandatory", violations.iterator().next().getMessage());
    }

    @Test
    public void testSubComments() {
        Comment parentComment = new Comment();
        Comment subComment1 = new Comment();
        Comment subComment2 = new Comment();

        parentComment.setSubComments(List.of(subComment1, subComment2));
        subComment1.setParentComment(parentComment);
        subComment2.setParentComment(parentComment);

        assertEquals(2, parentComment.getSubComments().size());
        assertEquals(parentComment, subComment1.getParentComment());
        assertEquals(parentComment, subComment2.getParentComment());
    }

    @Test
    public void testEqualsAndHashCode() {
        UUID id = UUID.randomUUID();
        Comment comment1 = Comment.builder().id(id).content("Test").build();
        Comment comment2 = Comment.builder().id(id).content("Test").build();
        Comment comment3 = Comment.builder().id(UUID.randomUUID()).content("Test").build();

        assertEquals(comment1, comment2);
        assertNotEquals(comment1, comment3);
        assertEquals(comment1.hashCode(), comment2.hashCode());
        assertNotEquals(comment1.hashCode(), comment3.hashCode());
    }

    @Test
    public void testToString() {
        Comment comment = Comment.builder()
                .id(UUID.randomUUID())
                .content("Test comment")
                .creationDate(Timestamp.from(Instant.now()))
                .build();

        String commentString = comment.toString();
        assertTrue(commentString.contains("id="));
        assertTrue(commentString.contains("content=Test comment"));
        assertTrue(commentString.contains("creationDate="));
    }
}