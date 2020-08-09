package com.nightshine.vestigate.service.user;

import com.nightshine.vestigate.exception.user.UserNotFound;
import com.nightshine.vestigate.model.user.User;
import com.nightshine.vestigate.payload.request.user.LoginRequest;
import com.nightshine.vestigate.payload.request.user.SignUpRequest;
import com.nightshine.vestigate.payload.request.user.UserUpdateRequest;
import com.nightshine.vestigate.payload.response.ApiResponse;
import com.nightshine.vestigate.repository.user.UserRepository;
import com.nightshine.vestigate.security.JwtTokenProvider;
import com.nightshine.vestigate.utils.Helper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.*;

@Service
@Transactional
public class AuthService {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    JwtTokenProvider tokenProvider;

    public String validateUser(LoginRequest loginRequest) {

        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                loginRequest.getUsernameOrEmail(),
                loginRequest.getPassword()
        );

        Authentication authentication = authenticationManager.authenticate(
                usernamePasswordAuthenticationToken
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return tokenProvider.generateToken(authentication);
    }

    public Map<String, String> authenticateUser(String token) {
        Map<String, String> userDetails = new HashMap<>();
        if(userRepository.findById(UUID.fromString(tokenProvider.getUserIdFromJWT(token))).isPresent()) {
            userDetails.put("status", "Authentication Successful");
            userDetails.put("email", userRepository.findById(UUID.fromString(tokenProvider.getUserIdFromJWT(token))).get().getEmail());
            userDetails.put("username", userRepository.findById(UUID.fromString(tokenProvider.getUserIdFromJWT(token))).get().getUsername());
            userDetails.put("role", userRepository.findById(UUID.fromString(tokenProvider.getUserIdFromJWT(token))).get().getRoleType().toString());
        }
        else {
            userDetails.put("status", "Authentication unsuccessful!");
        }
        return userDetails;
    }

    public ResponseEntity<?> registerUser(SignUpRequest signUpRequest) {
        if(userRepository.findByUsername(signUpRequest.getUsername()).isPresent()) {
            return new ResponseEntity(new ApiResponse(false, "Username is already taken!"),
                    HttpStatus.BAD_REQUEST);
        }

        if(userRepository.existsByEmail(signUpRequest.getEmail())) {
            return new ResponseEntity(new ApiResponse(false, "Email Address already in use!"),
                    HttpStatus.BAD_REQUEST);
        }
        // Creating user's account
        User user = new User();
        user.setName(signUpRequest.getName());
        user.setUsername(signUpRequest.getUsername());
        user.setEmail(signUpRequest.getEmail());
        user.setPassword(signUpRequest.getPassword());
        user.setPosition(signUpRequest.getPosition());
        user.setRoleType(signUpRequest.getRoleType());
        user.setImage(signUpRequest.getImage());

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        User result = userRepository.save(user);

        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/api/users/{username}")
                .buildAndExpand(result.getUsername()).toUri();

        return ResponseEntity.created(location).body(new ApiResponse(true, "User registered successfully"));
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<User> getSingleUser(String usernameOrEmail) {
        return userRepository.findByUsername(usernameOrEmail);
    }

    public User updateUser(UserUpdateRequest userUpdateRequest, String username) throws UserNotFound {
        Optional<User> optionalUser = userRepository.findByUsername(username);
        if(!optionalUser.isPresent() || !userRepository.findByUsername(username).isPresent()) {
            throw new UserNotFound( "User doesn't exists!");
        }
        User user = optionalUser.get();
        Helper.copyUserDetails(user, userUpdateRequest);
        return userRepository.save(user);
    }

    public void removeUser(UUID userId) {
        userRepository.deleteById(userId);
    }

    public void removeMultipleUsers(List<UUID> ids) {
        userRepository.deleteAll(ids);
    }
}
