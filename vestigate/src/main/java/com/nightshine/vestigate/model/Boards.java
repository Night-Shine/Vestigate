package com.nightshine.vestigate.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.stereotype.Component;

import java.util.List;

@Getter
@Setter
@Component
@Document(collection = "boards")
@AllArgsConstructor
@NoArgsConstructor
public class Boards extends DateAudit {

    @Id
    private String id;


    private List<String> backlogs;

    @NonNull
    private String assigned;

    @Field
    private boolean idDeleted = false;

}
