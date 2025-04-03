package com.uade.tpo.marketplace.service.implementation;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.uade.tpo.marketplace.entity.Admin;
import com.uade.tpo.marketplace.exceptions.UserDuplicateException;
import com.uade.tpo.marketplace.repository.AdminRepository;
import com.uade.tpo.marketplace.service.AdminService;

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private AdminRepository adminRepository;

    public Page<Admin> getAdmins(PageRequest pageable) {
        return adminRepository.findAll(pageable);
    }

    public Optional<Admin> getAdminById(Long adminId) {
        return adminRepository.findById(adminId);
    }

    public Admin createAdmin(String userName, String password) throws UserDuplicateException {
        List<Admin> admins = adminRepository.findByUserName(userName);
        if (admins.isEmpty())
            return adminRepository.save(new Admin(userName,password));
        throw new UserDuplicateException();
    }
    
}

