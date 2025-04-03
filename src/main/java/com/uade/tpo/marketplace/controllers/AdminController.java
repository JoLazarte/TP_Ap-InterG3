package com.uade.tpo.marketplace.controllers;

import java.net.URI;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.uade.tpo.marketplace.entity.Admin;
import com.uade.tpo.marketplace.entity.dto.AdminRequest;
import com.uade.tpo.marketplace.exceptions.UserDuplicateException;
import com.uade.tpo.marketplace.service.AdminService;


@RestController
@RequestMapping("admins")
public class AdminController {
@Autowired
    private AdminService adminService;

    @GetMapping
    public ResponseEntity<Page<Admin>> getAdmins(
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer size) {
        if (page == null || size == null)
            return ResponseEntity.ok(adminService.getAdmins(PageRequest.of(0, Integer.MAX_VALUE)));
        return ResponseEntity.ok(adminService.getAdmins(PageRequest.of(page, size)));
    }

    @GetMapping("/{adminId}")
    public ResponseEntity<Admin> getAdminById(@PathVariable Long adminId) {
        Optional<Admin> result = adminService.getAdminById(adminId);
        if (result.isPresent())
            return ResponseEntity.ok(result.get());
        return ResponseEntity.noContent().build();
    }

    @PostMapping
    public ResponseEntity<Object> createBuyer(@RequestBody AdminRequest adminRequest)
            throws UserDuplicateException {
        Admin result = adminService.createAdmin(adminRequest.getUserName(), adminRequest.getPassword());
        return ResponseEntity.created(URI.create("/users/" + result.getId())).body(result);
    }

    
}
