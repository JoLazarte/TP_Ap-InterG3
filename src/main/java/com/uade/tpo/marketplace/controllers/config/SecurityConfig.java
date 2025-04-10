package com.uade.tpo.marketplace.controllers.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
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
                                                //Auth
                                                .requestMatchers("/auth/**").permitAll()
                                                // User
						.requestMatchers("/user/**").authenticated()
						// Product
						.requestMatchers(HttpMethod.GET, "/product/**").permitAll()
						.requestMatchers("/product/**").hasAuthority(Role.ADMIN.name())
                                                //Book
                                                .requestMatchers(HttpMethod.GET, "/book/**").permitAll()
                                                .requestMatchers("/book/**").hasAuthority(Role.ADMIN.name())
						//MusicAlbum
                                                .requestMatchers(HttpMethod.GET, "/musicAlbum/**").permitAll()
                                                .requestMatchers("/musicAlbum/**").hasAuthority(Role.ADMIN.name())

						// El resto de rutas aca
						// Cart
						.requestMatchers("/cart/**").authenticated()
                                                //CartItem
                                                .requestMatchers("/cartItem/**").authenticated()
                                                
						// Default
                                                .anyRequest().authenticated())
                                .sessionManagement(session -> session.sessionCreationPolicy(STATELESS))
                                .authenticationProvider(authenticationProvider)
                                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

                return http.build();
        }
}
