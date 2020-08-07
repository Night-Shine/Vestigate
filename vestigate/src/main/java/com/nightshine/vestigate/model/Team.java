package com.nightshine.vestigate.model;

import lombok.*;

import org.springframework.stereotype.Component;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.UUID;

@Component
@Entity
@Table(name="Team")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Team extends DateAudit{



    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column
    private UUID id;

    @NonNull
    @Column(name="team_name")
    @NotBlank
    private String teamName;

    @Column
    private String description;


    @Column(name="team_members")
    @ElementCollection
    private List<UUID> teamMembers;

    @Column
    @NonNull
    @NotBlank
    private UUID companyId;

    @Column
    @NonNull
    @NotBlank
    private  UUID projectId;

    @Column(name = "is_Deleted")
    private boolean isDeleted = false;


}
