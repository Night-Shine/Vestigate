package com.nightshine.vestigate.model;

import java.util.Collections;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.stereotype.Component;

@Component
@Document(collection="Task")
public class Task {
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getAssignee() {
		return assignee;
	}
	public void setAssignee(String assignee) {
		this.assignee = assignee;
	}
	public String getReporter() {
		return reporter;
	}
	public void setReporter(String reporter) {
		this.reporter = reporter;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public List<Task> getSubTask() {
		return subTask;
	}
	public void setSubTask(List<Task> subTask) {
		this.subTask = subTask;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getStoryPoints() {
		return storyPoints;
	}
	public void setStoryPoints(String storyPoints) {
		this.storyPoints = storyPoints;
	}
	public Boolean getIsDeleted() {
		return isDeleted;
	}
	public void setIsDeleted(Boolean isDeleted) {
		this.isDeleted = isDeleted;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
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
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	public Priority getPriority() {
		return priority;
	}
	public void setPriority(Priority priority) {
		this.priority = priority;
	}
	public Boolean getIsSubTask() {
		return isSubTask;
	}
	public void setIsSubTask(Boolean isSubTask) {
		this.isSubTask = isSubTask;
	}
	@Field
    private String userId = "";

}
