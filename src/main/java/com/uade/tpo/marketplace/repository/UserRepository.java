package com.uade.tpo.marketplace.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.uade.tpo.marketplace.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{
    @Query(value = "select u from User u where u.userName = ?1")
    List<User> findByUserName(String userName);
}
