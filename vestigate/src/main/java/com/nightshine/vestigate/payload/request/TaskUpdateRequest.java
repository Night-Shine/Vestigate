package com.nightshine.vestigate.payload.request;

import com.nightshine.vestigate.model.Priority;
import com.nightshine.vestigate.model.Task;

import java.util.Collections;
import java.util.List;



public class TaskUpdateRequest {
	private String title;
	private String description;
	private List<Task> subTask=Collections.emptyList();
	private String status;
	private String assignee;
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
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	public String getStoryPoints() {
		return storyPoints;
	}
	public void setStoryPoints(String storyPoints) {
		this.storyPoints = storyPoints;
	}
	public Priority getPriority() {
		return priority;
	}
	public void setPriority(Priority priority) {
		this.priority = priority;
	}
	private String reporter;
	private String comments;
	private String storyPoints;
	private Priority priority;
}
