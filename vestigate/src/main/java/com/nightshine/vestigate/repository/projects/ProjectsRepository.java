package com.nightshine.vestigate.repository.projects;

import com.nightshine.vestigate.model.Projects;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectsRepository extends MongoRepository<Projects,String> {

    @Query("{'companyId':?0,'isDeleted':false}")
    List<Projects> getProjectsByCompanyId(String companyId);

    @Query("{'id':?0,isDeleted:false}")
    Projects findByProjectId(String projectId);

    @Query("{'name':?0,isDeleted:false}")
    Projects findByProjectName(String pName);
}