package com.nightshine.vestigate.model;


import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

@Component
@Getter
@Setter
@Document(collection = "projects")
public class Projects {

    @Id
    private String id;


    @NonNull
    @Indexed(unique=true)
    public String companyId;

    @NonNull
    @Indexed(unique=true)
    public String companyName;


    private String description;

    private String companyUrl;

    public String image;

    @Field
    private Boolean isDeleted = false;

    @Field
    private List<String> teamId;

    @Field
    private List<String> boardsId;

}
