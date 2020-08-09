package com.nightshine.vestigate.payload.request.task;

import com.nightshine.vestigate.model.task.Priority;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SubTaskUpdateRequest {
    private String title;
    private String description;
    private String status;
    private String assignee;
    private String reporter;
    private String comments;
    private String storyPoints;
    private Priority priority;
}
