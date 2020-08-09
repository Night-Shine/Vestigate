package com.nightshine.vestigate.repository.team;

import com.nightshine.vestigate.model.team.Team;;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface TeamRepository extends JpaRepository<Team,UUID> {

    @Query("SELECT P FROM Team P WHERE P.isDeleted=false and P.projectId=:projectId")
    List<Team> getTeamsByProject(UUID projectId);

    @Query("SELECT P FROM Team P WHERE P.isDeleted=false and P.companyId=:companyId")
    List<Team> getTeamsByCompany(UUID companyId);

    @Query("SELECT P FROM Team P WHERE P.isDeleted=false and P.id=:id")
    Optional<Team> findById(UUID id);

    @Query("SELECT P FROM Team P WHERE P.isDeleted=false and P.id IN :teamIds")
    List<Team> getTeamsByIds(List<UUID> teamIds);

    @Modifying
    @Query("UPDATE Team c SET c.isDeleted=true WHERE c.id IN :ids")
    void deleteAll(List<UUID> ids);

    @Modifying
    @Query("UPDATE Team c SET c.isDeleted=true WHERE c.id=:id")
    void deleteById(UUID id);

}
