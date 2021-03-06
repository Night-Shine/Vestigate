package com.nightshine.vestigate.payload.request.user;

import com.nightshine.vestigate.model.user.RoleType;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

@Setter
@Getter
public class UserUpdateRequest {
    private String name;

    @Size(min = 3, max = 15)
    private String username;

    @Size(max = 40)
    @Email
    private String email;

    @Size(min = 6, max = 20)
    private String password;

    private RoleType roleType;

    @Size(min = 3, max = 40)
    private String position;

    private String image;
}
