package com.nightshine.vestigate.repository;

import com.nightshine.vestigate.model.Boards;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BoardsRepository extends MongoRepository<Boards,String> {

    @Query("{'id':{$in:?0}}")
    List<Boards> getTeamsByIds(List<String> boardIds);

}
