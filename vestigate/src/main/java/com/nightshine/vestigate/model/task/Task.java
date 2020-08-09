package com.nightshine.vestigate.model.task;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

import com.nightshine.vestigate.model.DateAudit;
import lombok.*;



import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Task")
public class Task extends DateAudit {
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
	@ElementCollection
	private List<SubTask> subTask=Collections.emptyList();
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
	private Boolean isSubTask = false;
	@Column
	private String userId = "";
}
