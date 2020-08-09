package com.nightshine.vestigate.payload.request.task;

import com.nightshine.vestigate.model.task.Priority;
import com.nightshine.vestigate.model.task.SubTask;
import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
public class TaskUpdateRequest {
	private String title;
	private String description;
	private List<SubTask> subTask;
	private String status;
	private String assignee;
	private String reporter;
	private String comments;
	private String storyPoints;
	private Priority priority;
}
