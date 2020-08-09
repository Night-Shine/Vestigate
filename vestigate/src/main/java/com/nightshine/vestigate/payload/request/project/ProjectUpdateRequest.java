package com.nightshine.vestigate.payload.request.project;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Size;

@Setter
@Getter
public class ProjectUpdateRequest {

    private String image;

    private String description;

    private String projectUrl;

    @Size(min = 3, max = 15)
    private String projectName;

}
