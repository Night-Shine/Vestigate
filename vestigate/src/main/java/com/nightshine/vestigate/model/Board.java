package com.nightshine.vestigate.model;

import lombok.*;

import org.springframework.stereotype.Component;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Component
@Entity
@Table(name="Board")
@AllArgsConstructor
@NoArgsConstructor
public class Board extends DateAudit {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column
    private UUID id;

    @Column
    @ElementCollection
    private List<UUID> backlogs;

    @NonNull
    @NotBlank
    @Column
    private String assigned;

    @Column(name = "is_Deleted")
    private boolean isDeleted = false;

}
