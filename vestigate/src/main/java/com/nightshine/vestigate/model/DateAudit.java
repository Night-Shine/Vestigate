package com.nightshine.vestigate.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.config.EnableMongoAuditing;

import javax.persistence.MappedSuperclass;
import java.io.Serializable;
import java.util.Date;

@MappedSuperclass
@JsonIgnoreProperties(
        value = {"createdAt", "updatedAt"},
        allowGetters = true
)
@Setter
@Getter
@EnableMongoAuditing
public abstract class DateAudit implements Serializable {

    @CreatedDate
    @NonNull
    private Date createdAt;

    @LastModifiedDate
    @NonNull
    private Date updatedAt;
}