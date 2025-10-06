package com.nurs.backend.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.nurs.backend.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);

    @Query(value = "SELECT * FROM users WHERE username = :username", nativeQuery = true)
    Optional<User> findRawByUsername(@Param("username") String username);

    @Query(value = "SELECT * FROM users WHERE id = :id", nativeQuery = true)
    Optional<User> findRawById(@Param("id") Long id);

    @Query("SELECT u FROM User u JOIN FETCH u.roles r LEFT JOIN FETCH u.account a WHERE u.username = :username ")
    Optional<User> findProfileDataByUsername(@Param("username") String username);

    @Query("SELECT u FROM User u JOIN FETCH u.roles r LEFT JOIN FETCH u.account a WHERE u.id = :userId")
    Optional<User> findProfileDataByUserId(@Param("userId") Long id);

    @Query(value = "SELECT u.* FROM users u  "+ 
        "LEFT JOIN accounts a ON a.user_id = u.id " + 
        "WHERE u.id = :userId",
        nativeQuery = true
    )
    Optional<User> findProfileDataByUserIdDeletedUser(@Param("userId") Long id);

    @Query("SELECT DISTINCT u FROM User u " +
        "JOIN FETCH u.roles r " +
        "LEFT JOIN FETCH u.account a " +
        "WHERE LOWER(u.username) LIKE LOWER(CONCAT(:keyword, '%')) OR " +
        "LOWER(a.accountId) LIKE LOWER(CONCAT(:keyword, '%')) OR " +
        "CAST(a.balance as string) LIKE CONCAT(:keyword, '%') "
    )
    Page<User> searchFullProfileData(@Param("keyword") String keyword, Pageable pageable);

    @Query("SELECT DISTINCT u FROM User u " +
        "JOIN FETCH u.roles r " +
        "LEFT JOIN FETCH u.account a "
    )
    Page<User> searchFullProfileData(Pageable pageable);

    @Query(value = "SELECT u.*, u.created_at AS createdAt FROM users u " +
        "LEFT JOIN accounts a ON a.user_id = u.id " +
        "WHERE u.is_deleted = true AND " +
        "( LOWER(u.username) LIKE LOWER(CONCAT(:keyword, '%')) OR " +
        "LOWER(a.account_id) LIKE LOWER(CONCAT(:keyword, '%')) OR " +
        "CAST(a.balance AS TEXT) LIKE CONCAT(:keyword, '%') )",
        nativeQuery = true
    )
    Page<User> searchFullProfileDataDeletedUsers(@Param("keyword") String keyword, Pageable pageable);

    @Query(value = "SELECT u.*, u.created_at AS createdAt FROM users u " +
        "LEFT JOIN accounts a ON a.user_id = u.id " +
        "WHERE u.is_deleted = true",
        nativeQuery = true
    )
    Page<User> searchFullProfileDataDeletedUsers(Pageable pageable);
}
