package com.nightshine.vestigate.payload.request.team;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Size;

@Setter
@Getter
public class TeamUpdateRequest {

    @Size(min = 3, max = 15)
    private String teamName;

    private String description;


}
