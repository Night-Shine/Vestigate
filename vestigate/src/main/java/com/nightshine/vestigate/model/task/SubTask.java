package com.nightshine.vestigate.model.task;

import java.util.UUID;

import com.nightshine.vestigate.model.DateAudit;
import lombok.*;
import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "SubTask")
public class SubTask extends DateAudit {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    @NonNull
    @Column
    private String title;
    @Column
    private String description;
    @Column
    private Status status=Status.TODO;
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
