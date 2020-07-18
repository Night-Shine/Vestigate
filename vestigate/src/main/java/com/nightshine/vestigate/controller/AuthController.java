package com.nightshine.vestigate.controller;

import com.nightshine.vestigate.exception.UserNotFound;
import com.nightshine.vestigate.model.User;
import com.nightshine.vestigate.payload.request.LoginRequest;
import com.nightshine.vestigate.payload.request.SignUpRequest;
import com.nightshine.vestigate.payload.request.UserUpdateRequest;
import com.nightshine.vestigate.security.JwtTokenProvider;
import com.nightshine.vestigate.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    AuthService authService;

    @PostMapping("/signIn")
    public ResponseEntity<Map<String, String>> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        String token = authService.validateUser(loginRequest);
        return new ResponseEntity<>(authService.authenticateUser(token), HttpStatus.CREATED);
    }

    @PostMapping("/signUp")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignUpRequest signUpRequest) {
        return authService.registerUser(signUpRequest);
    }

    @GetMapping("/allUsers")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = authService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/allUsers/{usernameOrEmail}")
    public ResponseEntity<Optional<User>> getSingleUser(@Valid @PathVariable String usernameOrEmail) {
        return new ResponseEntity<>(authService.getSingleUser(usernameOrEmail), HttpStatus.OK);
    }

    @PutMapping("/allUsers/{username}")
    public ResponseEntity<User> updateUser(@Valid @RequestBody UserUpdateRequest userUpdateRequest, @PathVariable String username) throws UserNotFound {
        User user = authService.updateUser(userUpdateRequest, username);
        return new ResponseEntity<>(user, HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/deleteUser/{userId}")
    public ResponseEntity<?> deleteUserById(@Valid @PathVariable String userId) {
        authService.removeUser(userId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @DeleteMapping("/deleteMultipleUsers")
    public ResponseEntity<?> deleteMultipleUsers(@Valid @RequestBody List<String> ids) {
        authService.removeMultipleUsers(ids);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping("/health")
    public ResponseEntity<String> health(){
        return new ResponseEntity<String>("alive",HttpStatus.OK);
    }
}