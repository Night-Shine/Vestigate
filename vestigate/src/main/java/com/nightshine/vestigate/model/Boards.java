package com.nightshine.vestigate.model;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.stereotype.Component;

import java.util.List;

@Getter
@Setter
@Component
@Document(collection = "boards")
public class Boards {

    @Id
    private String id;


    private List<String> backlogs;

    @NonNull
    private String assigned;

    @Field
    private boolean idDeleted = false;

}
