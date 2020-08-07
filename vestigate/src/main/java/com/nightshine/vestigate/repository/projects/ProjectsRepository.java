package com.nightshine.vestigate.repository.projects;

import com.nightshine.vestigate.model.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ProjectsRepository extends JpaRepository<Project,UUID> {

    @Query("SELECT P FROM Project P WHERE P.isDeleted=false and P.companyId=:companyId")
    List<Project> getProjectsByCompanyId(UUID companyId);

    @Query("SELECT P FROM Project P WHERE P.isDeleted=false and P.id=:projectId")
    Optional<Project> findById(UUID projectId);

    @Query("SELECT P FROM Project P WHERE P.isDeleted=false and P.projectName=:pName")
    Project findByProjectName(String pName);

    @Modifying
    @Query("UPDATE Project c SET c.isDeleted=true WHERE c.id=:id")
    void deleteById(UUID id);

    @Modifying
    @Query("UPDATE Project c SET c.isDeleted=true WHERE c.id IN :ids")
    public void deleteAll(List<UUID> ids);
}