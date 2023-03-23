package org.example.repository;

import org.example.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

    @Query("SELECT u FROM User u WHERE u.firstname = :firstname")
    Optional<User> findByFirstName(@Param("firstname") String firstname);

    @Query("SELECT u FROM User u WHERE u.id = :userId")
    Optional<User>  findByUserId(String userId);
}