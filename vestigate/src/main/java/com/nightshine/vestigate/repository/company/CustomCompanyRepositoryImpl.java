package com.nightshine.vestigate.repository.company;

import com.nightshine.vestigate.model.Company;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import java.util.List;

public class CustomCompanyRepositoryImpl<T, ID> implements CustomCompanyRepository<T, ID> {
    @Autowired
    MongoTemplate mongoTemplate;

    @Override
    public void deleteById(ID id) {
        Query query = new Query();
        query.addCriteria(Criteria.where("id").is(id));
        Update update = new Update();
        update.set("isDeleted", "true");
        mongoTemplate.findAndModify(query, update, Company.class);
    }

    @Override
    public void deleteAll(List<String> ids) {
        ids.forEach(id -> {
            Query query = new Query();
            query.addCriteria(Criteria.where("id").is(id));
            Update update = new Update();
            update.set("isDeleted", "true");
            mongoTemplate.findAndModify(query, update, Company.class);
        });
    }
}