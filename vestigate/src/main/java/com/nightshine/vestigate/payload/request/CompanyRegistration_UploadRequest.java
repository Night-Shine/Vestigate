package com.nightshine.vestigate.payload.request;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import javax.validation.constraints.Size;
import java.util.List;

@Setter
@Getter
public class CompanyRegistration_UploadRequest {

    @NonNull
    @Size(min = 4, max = 40)
    private String companyName; // (unique)

    @Size(min = 4, max = 100)
    private String description; //(optional)

    private List<String> projects;
}
