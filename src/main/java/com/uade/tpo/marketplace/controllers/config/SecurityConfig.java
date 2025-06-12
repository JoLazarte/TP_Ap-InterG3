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
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.uade.tpo.marketplace.entity.Role;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
        @Autowired
        private final JwtAuthenticationFilter jwtAuthFilter;
        @Autowired
        private final AuthenticationProvider authenticationProvider;

        @Bean
        public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
                http.cors(cors -> cors.configurationSource(corsConfigurationSource()))
                    .csrf(AbstractHttpConfigurer::disable)
                    .authorizeHttpRequests(req -> req
                            //Auth
                            .requestMatchers("/auth/**").permitAll()
                            // User
                            .requestMatchers("/users/**").authenticated()
                            //Book
                            .requestMatchers(HttpMethod.GET, "/books/**").permitAll()
                            .requestMatchers("/books/**").hasAuthority(Role.ADMIN.name())
                            //MusicAlbum
                            .requestMatchers(HttpMethod.GET, "/musicAlbums/**").permitAll()
                            .requestMatchers("/musicAlbums/**").hasAuthority(Role.ADMIN.name())
                            //Mockup
                            .requestMatchers(HttpMethod.GET, "/mockup/**").permitAll()
                            .requestMatchers("/mockup/**").hasAuthority(Role.ADMIN.name())
                            //Buy
                            .requestMatchers("/buys/**").authenticated()
                            // Cart
                            .requestMatchers("/carts/**").authenticated()
                            //CartItem
                            //.requestMatchers("/cartItems/**").authenticated()
                            
                            // Default
                            .anyRequest().authenticated())
                    .sessionManagement(session -> session.sessionCreationPolicy(STATELESS))
                    .authenticationProvider(authenticationProvider)
                    .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

                return http.build();
        }
        // Configuracion de CORS
	@Bean
	public UrlBasedCorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration corsConfig = new CorsConfiguration();
		corsConfig.setAllowedOrigins(List.of("*")); // Permite cualquier origen
		corsConfig.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS")); // Métodos permitidos
		corsConfig.setAllowCredentials(false); // Debe ser false cuando se usa "*" como origen
		corsConfig.addAllowedHeader("*"); // Permite todos los headers

		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", corsConfig);
		return source;
	}

}