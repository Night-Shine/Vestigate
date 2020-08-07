package com.nightshine.vestigate.payload.request;

import com.nightshine.vestigate.model.Priority;
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
