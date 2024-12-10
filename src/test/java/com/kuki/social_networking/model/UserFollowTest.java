package com.kuki.social_networking.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.sql.Timestamp;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class UserFollowTest {

    @Mock
    private UUID mockId;

    @Mock
    private Timestamp mockCreationDate;

    @Mock
    private User mockFollower;

    @Mock
    private User mockFollowed;

    /**
     * Test the creation of a UserFollow object and verifies that all fields are correctly set.
     */
    @Test
    public void testUserFollowCreation() {
        UserFollow userFollow = UserFollow.builder()
                .id(mockId)
                .creationDate(mockCreationDate)
                .follower(mockFollower)
                .followed(mockFollowed)
                .build();

        assertNotNull(userFollow);
        assertNotNull(userFollow.getId());
        assertNotNull(userFollow.getCreationDate());
        assertEquals(mockFollower, userFollow.getFollower());
        assertEquals(mockFollowed, userFollow.getFollowed());
    }

    /**
     * Tests the equality of two UserFollow objects with the same fields and ensures they are equal.
     * Also tests that two UserFollow objects with different IDs are not equal.
     */
    @Test
    public void testUserFollowEquality() {
        UserFollow userFollow1 = new UserFollow(mockId, mockCreationDate, mockFollower, mockFollowed);
        UserFollow userFollow2 = new UserFollow(mockId, mockCreationDate, mockFollower, mockFollowed);
        UserFollow userFollow3 = new UserFollow(UUID.randomUUID(), mockCreationDate, mockFollower, mockFollowed);

        assertEquals(userFollow1, userFollow2);
        assertNotEquals(userFollow1, userFollow3);
    }

    /**
     * Tests that two UserFollow objects with the same fields have the same hash code.
     */
    @Test
    public void testUserFollowHashCode() {
        UserFollow userFollow1 = new UserFollow(mockId, mockCreationDate, mockFollower, mockFollowed);
        UserFollow userFollow2 = new UserFollow(mockId, mockCreationDate, mockFollower, mockFollowed);

        assertEquals(userFollow1.hashCode(), userFollow2.hashCode());
    }
}

