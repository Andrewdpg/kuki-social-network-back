package com.kuki.social_networking.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.kuki.social_networking.model.User;
import com.kuki.social_networking.model.UserFollow;


/**
 * Repository for managing {@link User} entities.
 * Extends {@link JpaRepository} for basic CRUD operations and {@link JpaSpecificationExecutor} for complex queries.
 */
@Repository
public interface UserRepository extends JpaRepository<User, String>, JpaSpecificationExecutor<User> {

    /**
     * Finds a user by their email address.
     *
     * @param email the email address of the user.
     * @return an {@link Optional} containing the user if found, otherwise empty.
     */
    Optional<User> findByEmail(String email);

    /**
     * Finds users by their username or full name, ignoring case.
     *
     * @param username the username of the user.
     * @param fullName the full name of the user.
     * @return a list of users that match either the username or the full name.
     */
    List<User> findByUsernameContainingIgnoreCaseOrFullNameContainingIgnoreCase(String username, String fullName);

    /**
     * Retrieves the users followed by the specified user.
     *
     * @param username the username of the user.
     * @return a list of users followed by the specified user.
     */
    @Query("SELECT uf FROM UserFollow uf WHERE uf.follower.username = :username")
    Page<UserFollow> findFollowedByUser(@Param("username") String username, Pageable pageable);

    /**
     * Retrieves the number of users followed by the specified user (Seguidos).
     *
     * @param username the username of the user.
     * @return the number of users followed by the specified user.
     */
    @Query("SELECT COUNT(uf) FROM UserFollow uf WHERE uf.follower.username = :username")
    Integer countUsersFollowedByUser(@Param("username") String username);

    /**
     * Retrieves the followers of the specified user.
     *
     * @param username the username of the user being followed.
     * @return a list of users following the specified user.
     */
    @Query("SELECT uf FROM UserFollow uf WHERE uf.followed.username = :username")
    Page<UserFollow> findFollowersOfUser(@Param("username") String username, Pageable pageable);

    /**
     * Retrieves the number of users following the specified user (Seguidores).
     *
     * @param username the username of the user being followed.
     * @return the number of users following the specified user.
     */
    @Query("SELECT COUNT(uf) FROM UserFollow uf WHERE uf.followed.username = :username")
    Integer countUsersFollowers(String username);

    @Query("SELECT CASE WHEN COUNT(uf) > 0 THEN true ELSE false END FROM UserFollow uf WHERE uf.follower.username = :followerUsername AND uf.followed.username = :followedUsername")
    boolean isFollowing(@Param("followerUsername") String followerUsername, @Param("followedUsername") String followedUsername);

    Optional<User> findByUsername(String name);

    /**
     * Retrieves the users who liked the specified recipe.
     * Must validate that the recipe is accessible to the user.
     *
     * @param uuid the ID of the recipe.
     * @param username the username of the user querying.
     * @param pageable the pageable object for pagination.
     * @return a page of users who liked the specified recipe.
     */
    @Query("SELECT u FROM User u JOIN u.likes r WHERE r.id = :uuid AND (r.publishDate IS NOT NULL OR r.recipeOwner.username = :username)")
    Page<User> findUsersWhoLikedRecipe(UUID uuid, String username, Pageable pageable);
}
