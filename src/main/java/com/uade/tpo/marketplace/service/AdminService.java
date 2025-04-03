package com.uade.tpo.marketplace.service;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import com.uade.tpo.marketplace.entity.Admin;
import com.uade.tpo.marketplace.exceptions.UserDuplicateException;

public interface AdminService {

    public Page<Admin> getAdmins(PageRequest pageRequest);

    public Optional<Admin> getAdminById(Long userId);

    public Admin createAdmin(String userName, String password) throws UserDuplicateException;
    
}
