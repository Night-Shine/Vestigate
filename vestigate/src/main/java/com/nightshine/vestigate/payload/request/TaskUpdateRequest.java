package com.nightshine.vestigate.payload.request;

import com.nightshine.vestigate.model.Priority;
import com.nightshine.vestigate.model.Task;
import lombok.Getter;
import lombok.Setter;

import java.util.Collections;
import java.util.List;


@Getter
@Setter
public class TaskUpdateRequest {
	private String title;
	private String description;
	private List<Task> subTask=Collections.emptyList();
	private String status;
	private String assignee;
	private String reporter;
	private String comments;
	private String storyPoints;
	private Priority priority;
}
