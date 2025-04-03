package com.uade.tpo.marketplace.service;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import com.uade.tpo.marketplace.entity.User;
import com.uade.tpo.marketplace.exceptions.UserDuplicateException;

public interface UserService {
    public Page<User> getUsers(PageRequest pageRequest);

    public Optional<User> getUserById(Long userId);

    public User createUser(String userName, String password) throws UserDuplicateException;
}
