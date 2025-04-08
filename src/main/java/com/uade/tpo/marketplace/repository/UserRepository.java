package com.uade.tpo.marketplace.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.uade.tpo.marketplace.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String mail);
}
/*
@Repository
public interface UserRepository extends JpaRepository<User, Long>{
    @Query(value = "select u from User u where u.email = ?1")
    List<User> findByEmail(String email);
}
*/