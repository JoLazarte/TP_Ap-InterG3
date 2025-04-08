package com.uade.tpo.marketplace.service.implementation;

import java.util.Optional;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.uade.tpo.marketplace.entity.User;
import com.uade.tpo.marketplace.repository.UserRepository;
import com.uade.tpo.marketplace.service.UserService;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepository userRepository;

    public Page<User> getUsers(PageRequest pageable) {
        return userRepository.findAll(pageable);
    }

    public Optional<User> getUserById(Long userId) {
        return userRepository.findById(userId);
    } 
}
