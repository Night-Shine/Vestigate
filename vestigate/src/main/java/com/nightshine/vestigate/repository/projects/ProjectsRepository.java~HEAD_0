package com.nightshine.vestigate.repository.projects;

import com.nightshine.vestigate.model.Project;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectsRepository extends MongoRepository<Project,String>, CustomProjectRepository<Project, String> {

    @Query("{'companyId':?0,'isDeleted':false}")
    List<Project> getProjectsByCompanyId(String companyId);

    @Query("{'id':?0,isDeleted:false}")
    Project findByProjectId(String projectId);

    @Query("{'name':?0,isDeleted:false}")
    Project findByProjectName(String pName);
}