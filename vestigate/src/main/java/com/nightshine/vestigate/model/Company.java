package com.nightshine.vestigate.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import java.util.List;

@Setter
@Getter
@Document("Company")
@AllArgsConstructor
@NoArgsConstructor
public class Company extends DateAudit{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    @NonNull
    private String companyName; // (unique)

    private String description; //(optional)

    private List<String> projects; //(Company-Project mapping)

    private boolean isDeleted;

    public Company(String companyName, String description, List<String> projects) {
        this.id = id;
        this.companyName = companyName;
        this.description = description;
        this.projects = projects;
    }
}
