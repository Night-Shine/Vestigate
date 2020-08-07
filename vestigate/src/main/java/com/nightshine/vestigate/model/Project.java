package com.nightshine.vestigate.model;


import lombok.*;

import org.springframework.stereotype.Component;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Component
@Getter
@Setter
@Entity
@Table(name="Project")
@AllArgsConstructor
@NoArgsConstructor
public class Project extends DateAudit {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column
    private UUID id;


    @Column
    @NonNull
    @NotBlank
    public UUID companyId;

    @Column
    @NonNull
    @NotBlank
    public String projectName;

    @Column
    private String description;

    @Column
    private String projectUrl;

    @Column
    public String image;

    @Column(name = "is_Deleted")
    private Boolean isDeleted = false;

    @Column
    @ElementCollection
    private List<UUID> teamId;

    @Column
    @ElementCollection
    private List<UUID> boardsId;

}
