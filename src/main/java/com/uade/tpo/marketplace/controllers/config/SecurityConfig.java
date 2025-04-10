package com.uade.tpo.marketplace.controllers.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.uade.tpo.marketplace.entity.Role;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

        private final JwtAuthenticationFilter jwtAuthFilter;
        private final AuthenticationProvider authenticationProvider;

        @Bean
        public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(req -> req
                // Auth endpoints
                .requestMatchers("/auth/**").permitAll()

                // Products (solo lectura pública, resto solo admin)
                .requestMatchers(HttpMethod.GET, "/products/**").permitAll()
                .requestMatchers("/products/**").hasAuthority(Role.ADMIN.name())

                // Books
                .requestMatchers(HttpMethod.GET, "/books/**").permitAll()
                .requestMatchers("/books/**").hasAuthority(Role.ADMIN.name())

                // Music Albums
                .requestMatchers(HttpMethod.GET, "/musicAlbums/**").permitAll()
                .requestMatchers("/musicAlbums/**").hasAuthority(Role.ADMIN.name())

                // Carts y CartItems (solo BUYER)
                .requestMatchers("/carts/**", "/cart-items/**").hasAuthority(Role.BUYER.name())

                // Buys y purchase documents (solo BUYER)
                .requestMatchers("/buys/**", "/purchasedocuments/**").hasAuthority(Role.BUYER.name())

                // User management (solo ADMIN)
                .requestMatchers("/users/**").hasAuthority(Role.ADMIN.name())

                // Todo lo que no esté listado: requiere login
                .anyRequest().authenticated()
                )
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
        }
}
