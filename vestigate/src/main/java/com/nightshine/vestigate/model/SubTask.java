package com.nightshine.vestigate.model;

import java.util.UUID;
import lombok.*;
import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "SubTask")
public class SubTask extends DateAudit{
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    @NonNull
    @Column
    private String title;
    @Column
    private String description;
    @NonNull
    @Column
    private String status;
    @NonNull
    @Column
    private String assignee;
    @NonNull
    @Column
    private String reporter;
    @Column
    private String comments;
    @Column
    private String storyPoints;
    @Column
    private Priority priority;
    @Column(name="is_deleted")
    private Boolean isDeleted = false;
    @Column
    private Boolean isSubTask = true;
    @Column
    private String userId = "";
}
