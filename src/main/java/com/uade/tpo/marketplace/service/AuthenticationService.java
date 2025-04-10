package com.uade.tpo.marketplace.service;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.uade.tpo.marketplace.controllers.auth.AuthenticationRequest;
import com.uade.tpo.marketplace.controllers.auth.AuthenticationResponse;
import com.uade.tpo.marketplace.controllers.auth.RegisterRequest;
import com.uade.tpo.marketplace.controllers.config.JwtService;
import com.uade.tpo.marketplace.entity.User;
import com.uade.tpo.marketplace.exceptions.UserException;
import com.uade.tpo.marketplace.repository.UserRepository;

import jakarta.security.auth.message.AuthException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
        private final UserRepository userRepository;
        private final PasswordEncoder passwordEncoder;
        private final JwtService jwtService;
        private final AuthenticationManager authenticationManager;

        public AuthenticationResponse register(RegisterRequest request) throws Exception {
                try{
                        User user = User.builder()
                                .username(request.getUsername())
                                .firstName(request.getFirstName())
                                .lastName(request.getLastName())
                                .email(request.getEmail())
                                .password(passwordEncoder.encode(request.getPassword()))
                                .role(request.getRole()) 
                                .build();

                        userRepository.save(user);
                        String jwtToken = jwtService.generateToken(user);
                        return AuthenticationResponse.builder()
                                .accessToken(jwtToken)
                                .build();
                }catch(UserException error){
                        throw new UserException(error.getMessage());
                      }catch (Exception error){
                        throw new Exception("[AuthenticationService.register] -> " + error.getMessage());
                }
        }

        public AuthenticationResponse authenticate(AuthenticationRequest request) throws Exception {
                try{
                        authenticationManager.authenticate(
                                new UsernamePasswordAuthenticationToken(
                                                request.getUsername(),
                                                request.getPassword()));
                User user = userRepository.findByUsername(request.getUsername())
                        .orElseThrow(() -> new UserException("El usuario " + request.getUsername() + " no existe."));
                String jwtToken = jwtService.generateToken(user);
                return AuthenticationResponse.builder()
                        .accessToken(jwtToken)
                        .build();
                } catch (AuthenticationException error){
                        System.out.printf("[AuthenticationService.authenticate] -> %s", error.getMessage());
                                throw new AuthException("Usuario o contraseña incorrecto.");
                        }catch (UserException error) {
                                throw new UserException(error.getMessage());
                        }catch (Exception error) {
                                throw new Exception("[AuthenticationService.authenticate] -> " + error.getMessage());
                        }
        }
}
