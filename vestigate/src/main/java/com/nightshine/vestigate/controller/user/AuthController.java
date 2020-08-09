package com.nightshine.vestigate.controller.user;

import com.nightshine.vestigate.exception.user.UserNotFound;
import com.nightshine.vestigate.model.user.User;
import com.nightshine.vestigate.payload.request.user.LoginRequest;
import com.nightshine.vestigate.payload.request.user.SignUpRequest;
import com.nightshine.vestigate.payload.request.user.UserUpdateRequest;
import com.nightshine.vestigate.service.user.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    AuthService authService;

    @PostMapping("/signIn")
    public ResponseEntity<Map<String, String>> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        String token = authService.validateUser(loginRequest);
        Map<String, String> response = authService.authenticateUser(token);
        if(response.get("status").equals("Authentication Successful"))
            return new ResponseEntity<>(response, HttpStatus.FOUND);
        else
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @PostMapping("/signUp")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignUpRequest signUpRequest) {
        return new ResponseEntity<>(authService.registerUser(signUpRequest).getBody(), HttpStatus.CREATED);
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
    public ResponseEntity<?> deleteUserById(@Valid @PathVariable UUID userId) {
        authService.removeUser(userId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @DeleteMapping("/deleteMultipleUsers")
    public ResponseEntity<?> deleteMultipleUsers(@Valid @RequestBody List<UUID> ids) {
        authService.removeMultipleUsers(ids);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping("/health")
    public ResponseEntity<String> health(){
        return new ResponseEntity<String>("alive",HttpStatus.OK);
    }
}
