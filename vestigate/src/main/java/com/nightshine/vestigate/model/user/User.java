package com.nightshine.vestigate.model.user;

import com.nightshine.vestigate.model.DateAudit;
import lombok.*;

import javax.persistence.*;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "\"Vestigate_User\"")
public class User extends DateAudit {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @NonNull
    @Column(name = "name")
    private String name;

    @NonNull
    @Column(name = "username")
    private String username;

    @NonNull
    @Column(name = "email")
    private String email;

    @NonNull
    @Column(name = "password")
    private String password;

    @NonNull
    @Column(name = "position")
    private String position;

    @NonNull
    @Column(name = "role_type")
    private RoleType roleType;

    @Column(name = "image")
    private String image;

    //Task type should be of Task object. It should be changed from String to Task object
    @Column(name = "task")
    private String task;

    @Column(name = "is_deleted")
    private boolean isDeleted=false;
}
