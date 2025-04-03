package com.uade.tpo.marketplace.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.uade.tpo.marketplace.entity.Admin;

public interface AdminRepository extends JpaRepository<Admin, Long> {
    @Query(value = "select a from Admin a where a.userName = ?1")
    List<Admin> findByUserName(String userName);
}
