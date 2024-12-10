package com.kuki.social_networking.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.kuki.social_networking.model.User;
import com.kuki.social_networking.model.UserFollow;

/**
 * Repository interface for performing CRUD operations on UserFollow entities.
 */
@Repository
public interface UserFollowRepository extends CrudRepository<UserFollow, UUID> {

    /**
     * Checks if a UserFollow entity exists with the specified follower and followed users.
     * @param follower the user who is following another user
     * @param followed the user who is being followed
     * @return true if a UserFollow entity exists with the specified follower and followed users, otherwise false
     */
    boolean existsByFollowerAndFollowed(User follower, User followed);

    /**
     * Finds all UserFollow entities where the follower is the specified user.
     *
     * @param follower the user who is following other users
     * @return a list of UserFollow entities where the follower is the specified user
     */
    Optional<UserFollow> findByFollowerAndFollowed(User follower, User followed);
}
