package com.nightshine.vestigate.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import java.util.Set;

@Getter
@Setter
@Document("User")
@AllArgsConstructor
@NoArgsConstructor
public class User extends DateAudit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    @NonNull
    private String name;

    @NonNull
    private String username;

    @NonNull
    private String email;

    @NonNull
    private String password;

    @NonNull
    private String position;

    @NonNull
    private Set<RoleType> roleType;

    private String image;

    //Task type should be of Task object. It should be changed from String to Task object
    private String task;

    private boolean isDeleted=false;

    public User(String name, String username, String email, String password, String position, Set<RoleType> roleTypes, String image) {
        this.id = id;
        this.name = name;
        this.username = username;
        this.email = email;
        this.password = password;
        this.roleType = roleTypes;
        this.position = position;
        this.image = image;
    }
}
