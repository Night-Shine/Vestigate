package com.nightshine.vestigate.model.company;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.nightshine.vestigate.model.DateAudit;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Company")
public class Company extends DateAudit {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @NotNull
    @Column
    private String companyName; // (unique)

    @Column
    private String description; //(optional)

    @Column
    @ElementCollection
    private List<UUID> projects; //(Company-Project mapping)

    @Column
    private boolean isDeleted=false;
}
