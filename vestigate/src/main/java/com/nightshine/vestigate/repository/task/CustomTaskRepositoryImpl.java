package com.nightshine.vestigate.repository.task;

import java.util.List;

import com.nightshine.vestigate.model.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;


public class CustomTaskRepositoryImpl <T, ID> implements CustomTaskRepository<T, ID> {

    @Autowired
    MongoTemplate mongoTemplate;

    @Override
    public void deleteAll(List<String> ids) {
        Query query = new Query();
        query.addCriteria(Criteria.where("id").in(ids));
        Update update = new Update();
        update.set("isDeleted", "true");
        mongoTemplate.updateMulti(query, update, Task.class);
    }
}