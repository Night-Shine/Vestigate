package com.nightshine.vestigate.model;

import java.util.Collections;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@Document(collection="Task")
public class Task {
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
	private String title;
	private String description;
	@Field
	private List<Task> subTask=Collections.emptyList();
	private String status;
	private String assignee;
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
