package com.nightshine.vestigate.model.project;


import com.nightshine.vestigate.model.DateAudit;
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
    private  UUID companyId;

    @Column
    @NonNull
    @NotBlank
    private  String projectName;

    @Column
    private String description;

    @Column
    private String projectUrl;

    @Column
    private  String image;

    @Column(name = "is_Deleted")
    private Boolean isDeleted = false;

    @Column
    @ElementCollection
    private List<UUID> teamId;

    @Column
    @ElementCollection
    private List<UUID> boardsId;

}
