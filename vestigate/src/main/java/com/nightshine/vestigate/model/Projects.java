package com.nightshine.vestigate.model;


import com.mongodb.lang.NonNull;
import lombok.Getter;
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
@Document(collection = "Projects")
public class Projects {

    @Id
    private String id;


    @NonNull
    @Indexed(unique=true)
    public String companyId;

    @NonNull
    @Indexed(unique=true)
    public String name;


    private String description;

    private String _url;

    public String image;

    @Field
    private Boolean isDeleted = false;

    @Field
    private List<String> teamId = Collections.EMPTY_LIST;

    @Field
    private List<String> boardsId = Collections.EMPTY_LIST;

}
