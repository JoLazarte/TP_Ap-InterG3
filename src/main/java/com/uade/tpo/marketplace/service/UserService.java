package com.uade.tpo.marketplace.service;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import com.uade.tpo.marketplace.controllers.auth.RegisterRequest;
import com.uade.tpo.marketplace.entity.User;

import jakarta.transaction.Transactional;

public interface UserService {

    @Transactional
    public User createUser(RegisterRequest request) throws Exception;

    public User getUserByUsername(String username) throws Exception;

    public Page<User> getUsers(PageRequest pageRequest);

    public Optional<User> getUserById(Long userId);
}
