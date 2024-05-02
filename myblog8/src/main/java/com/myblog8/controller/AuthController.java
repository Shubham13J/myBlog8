package com.myblog8.controller;

import com.myblog8.entity.Role;
import com.myblog8.entity.User;
import com.myblog8.payload.LoginDto;
import com.myblog8.payload.SignUpDto;
import com.myblog8.repository.RoleRepository;
import com.myblog8.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashSet;
import java.util.Set;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private AuthenticationManager authenticationManager;


    @PostMapping("/signUp")
    public ResponseEntity<?> createUser (@RequestBody SignUpDto dto){

        if(userRepository.existsByEmail(dto.getEmail())){
            return new ResponseEntity<>("user already exist", HttpStatus.BAD_REQUEST);
        }
        if(userRepository.existsByUsername(dto.getUsername())){
            return new ResponseEntity<>("user already exist", HttpStatus.BAD_REQUEST);
        }

        User user = new User();
        user.setName(dto.getName());
        user.setUsername(dto.getUsername());
        user.setEmail(dto.getEmail());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));

        Role roles = roleRepository.findByName("ROLE_ADMIN").get();
        Set<Role> role = new HashSet<>();
        role.add(roles);
        user.setRoles(role);

        User savedUser = userRepository.save(user);

        SignUpDto dtos = new SignUpDto();
        dtos.setName(savedUser.getName());
        dtos.setUsername(savedUser.getUsername());
        dtos.setEmail(savedUser.getEmail());

        return new ResponseEntity<>(dto, HttpStatus.CREATED);

    }

    @PostMapping("/signin")
    public ResponseEntity<String> authenticateUser(@RequestBody LoginDto loginDto){
        Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginDto.getUsernameOrEmail(), loginDto.getPassword()
        ));
        SecurityContextHolder.getContext().setAuthentication(authenticate);
        return new ResponseEntity<>("User signed-in successfully!", HttpStatus.OK);
    }
}

