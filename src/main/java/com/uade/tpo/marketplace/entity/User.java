package com.uade.tpo.marketplace.entity;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.uade.tpo.marketplace.controllers.users.UserDTO;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class User implements UserDetails{
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String username;
    @Column
    private String firstName;
    @Column
    private String lastName;
    @Column(unique = true)
    private String email;
    @Column
    private String password;
    @Enumerated(EnumType.STRING)
    private Role role;
    
    @NotNull
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Buy> orders;

    @NotNull
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL ,orphanRemoval = true)
    @JsonManagedReference
    private List<WishListMusicAlbum> wishListMalbums;

    @NotNull
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL ,orphanRemoval = true)
    @JsonManagedReference
    private List<WishListBook> wishListBooks;

    @NotNull
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<SearchBook> lastBookSearches;

    @NotNull
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<SearchMusicAlbum> lastMalbumSearches;

    public UserDTO toDTO() {
        return new UserDTO(
            this.id,
            this.username,
            this.firstName,
            this.lastName,
            this.email,
            this.password,
            this.role,
            this.orders,
            this.wishListMalbums,
            this.wishListBooks,
            this.lastBookSearches,
            this.lastMalbumSearches
            );
    }

    public void updateData(User newUser){
        setFirstName(newUser.getFirstName());
        setLastName(newUser.getLastName());
        setEmail(newUser.getEmail());
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    
}