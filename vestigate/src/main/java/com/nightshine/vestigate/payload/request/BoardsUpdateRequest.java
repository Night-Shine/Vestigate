package com.nightshine.vestigate.payload.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Size;

@Setter
@Getter
public class BoardsUpdateRequest {

    @Size(min = 3, max = 15)
    private String assigned;
}
