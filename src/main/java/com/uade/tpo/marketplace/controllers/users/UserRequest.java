package com.uade.tpo.marketplace.controllers.users;

import com.uade.tpo.marketplace.entity.Role;
import lombok.Data;

@Data
public class UserRequest {
    private Long id;
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private String email;
    private Role role;
}
