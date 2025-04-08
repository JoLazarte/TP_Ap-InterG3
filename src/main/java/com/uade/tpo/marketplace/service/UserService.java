package com.uade.tpo.marketplace.service;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import com.uade.tpo.marketplace.entity.User;

public interface UserService {
    public Page<User> getUsers(PageRequest pageRequest);

    public Optional<User> getUserById(Long userId);
}
