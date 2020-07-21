package com.nightshine.vestigate.model;

import java.util.Collections;
import java.util.List;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@Document(collection="Task")
public class Task extends DateAudit{
	public Task() {
	}
	public Task(String title, String description,  String status, String assignee,
			String reporter, String comments, String storyPoints, Priority priority, String userId) {
		super();
		this.title = title;
		this.description = description;
		this.status = status;
		this.assignee = assignee;
		this.reporter = reporter;
		this.comments = comments;
		this.storyPoints = storyPoints;
		this.priority = priority;
		this.userId = userId;
	}

	@Id
	private String id;
	@NonNull
	private String title;
	private String description;
	@Field
	private List<Task> subTask=Collections.emptyList();
	@NonNull
	private String status;
	@NonNull
	private String assignee;
	@NonNull
	private String reporter;
	private String comments;
	private String storyPoints;
	private Priority priority;
	@Field
    private Boolean isDeleted = false;
	@Field
    private Boolean isSubTask = false;
	@Field
    private String userId = "";
}
