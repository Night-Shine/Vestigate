package com.nightshine.vestigate.payload.request.company;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import javax.validation.constraints.Size;
import java.util.List;
import java.util.UUID;

@Setter
@Getter
public class CompanyRegistration_UpdateRequest {

    @NonNull
    @Size(min = 4, max = 40)
    private String companyName; // (unique)

    @Size(min = 4, max = 100)
    private String description; //(optional)

    private List<UUID> projects;
}
