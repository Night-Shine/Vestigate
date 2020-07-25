package com.nightshine.vestigate.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

@Component
@Document(collection = "Team")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Team extends DateAudit{



    @Id
    private String id;

    @NonNull
    @Indexed(unique = true)
    private String teamName;

    private String description;

    @Field
    private List<String> teamMembers;

    @NonNull
    private String companyId;

    @NonNull
    private  String projectId;

    @Field
    private boolean isDeleted = false;


}
