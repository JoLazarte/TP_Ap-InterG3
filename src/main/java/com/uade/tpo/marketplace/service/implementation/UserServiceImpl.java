package com.uade.tpo.marketplace.service.implementation;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.uade.tpo.marketplace.controllers.auth.RegisterRequest;
import com.uade.tpo.marketplace.entity.Role;
import com.uade.tpo.marketplace.entity.User;
import com.uade.tpo.marketplace.exceptions.UserException;
import com.uade.tpo.marketplace.repository.UserRepository;
import com.uade.tpo.marketplace.service.UserService;

import jakarta.transaction.Transactional;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

	  @Transactional
    public User createUser(RegisterRequest request) throws Exception {
			try {
				boolean userExist = userRepository.existsByUsername(request.getUsername());
          if(userExist) throw new UserException("El usuario " + request.getUsername() + " ya existe");
            userExist = userRepository.existsByEmail(request.getEmail());
          if(userExist) throw new UserException("El email " + request.getEmail() + " ya esta registrado.");

        
				User user = new User(null,request.getUsername(), request.getFirstName(), request.getLastName(),
								request.getEmail(),
								passwordEncoder.encode(request.getPassword()),
                request.getRole(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
        

        return userRepository.save(user);
        
				
			} catch (UserException error) {
                throw new UserException(error.getMessage());
            } catch (Exception error) {
				throw new Exception("[UserService.createUser] -> " + error.getMessage());
			}
    }

    public User getUserByUsername(String username) throws Exception {
        try {
          return userRepository.findByUsername(username).orElseThrow(() -> new UserException("Usuario no encontrado"));
        } catch (UserException error) {
          throw new UserException(error.getMessage());
        } catch (Exception error) {
          throw new Exception("[UserService.getUserByUsername] -> " + error.getMessage());
        }
    }

    public Page<User> getUsers(PageRequest pageable) throws Exception {
        try {
          return userRepository.findAll(pageable);
        } catch (Exception error) {
          throw new Exception("[UserService.getAllUsers] -> " + error.getMessage());
        }
    }
    public User updateUser(User user) throws Exception {
        try {
          return userRepository.save(user);
        } catch (Exception error) {
          throw new Exception("[UserService.updateUser] -> " + error.getMessage());
        }
    }

    public Optional<User> getUserById(Long userId)throws Exception {
        try{
        return userRepository.findById(userId);
        }  catch (Exception error) {
            throw new Exception("[UserService.updateUser] -> " + error.getMessage());
          }
    } 
}
