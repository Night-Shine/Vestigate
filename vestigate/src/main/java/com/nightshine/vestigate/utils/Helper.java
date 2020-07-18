package com.nightshine.vestigate.utils;

import com.nightshine.vestigate.model.RoleType;
import com.nightshine.vestigate.model.User;
import com.nightshine.vestigate.payload.request.UserUpdateRequest;
import org.springframework.context.annotation.Configuration;

import java.util.HashSet;
import java.util.Set;

@Configuration
public class Helper {

    public static void copyUserDetails(User user, UserUpdateRequest userRequest) {
        if (userRequest.getName() != null)
            user.setName(userRequest.getName());
        if (userRequest.getEmail() != null)
            user.setEmail(userRequest.getEmail());
        if (userRequest.getUsername() != null)
            user.setUsername(userRequest.getUsername());
        if (userRequest.getPassword() != null)
            user.setPassword(userRequest.getPassword());
        if (userRequest.getPosition() != null)
            user.setPosition(userRequest.getPosition());
        if (userRequest.getRoleType() != null) {
            Set<RoleType> roleTypes = new HashSet<>();
            roleTypes.add(userRequest.getRoleType());
            user.setRoleType(roleTypes);
        }
        if (userRequest.getImage() != null)
            user.setImage(userRequest.getImage());
    }
}