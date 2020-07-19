package com.nightshine.vestigate.repository;

import com.nightshine.vestigate.model.Team;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TeamRepository extends MongoRepository<Team,String> {

    @Query("{'projectId':?0,'isDeleted':false}")
    List<Team> getTeamsByProject(String projectId);

    @Query("{'companyId':?0,'isDeleted':false}")
    List<Team> getTeamsByCompany(String companyId);

    @Query("{'id':?0,'isDeleted':false}")
    Team getTeamById(String id);

    @Query("{'id':{$in:?0}}")
    List<Team> getTeamsByIds(List<String> teamIds);

}
