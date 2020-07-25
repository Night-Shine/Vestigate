package com.nightshine.vestigate.service;



import com.mongodb.client.result.UpdateResult;
import com.nightshine.vestigate.exception.CompanyNotFound;
import com.nightshine.vestigate.exception.TeamNotFound;
import com.nightshine.vestigate.model.Team;
import com.nightshine.vestigate.payload.request.TeamUpdateRequest;
import com.nightshine.vestigate.repository.teams.CustomTeamRepositoryImpl;
import com.nightshine.vestigate.repository.teams.TeamRepository;
import com.nightshine.vestigate.utils.Helper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class TeamService {

    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private ProjectService projectService;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private CustomTeamRepositoryImpl customTeamRepository;

    public void saveTeam(Team team, String id){
        team.setCompanyId(id);
        Team savedTeam = teamRepository.save(team);

    }

    public void saveTeam1(Team team,String projectId) throws Throwable {
        team.setCompanyId(projectService.getCompanyId(projectId));
        team.setProjectId(projectId);

        Team savedTeam = teamRepository.save(team);
        String teamName = savedTeam.getTeamName();
        String companyId = savedTeam.getCompanyId();
        if(companyId == null)
            throw new CompanyNotFound("Company does not exists");
        boolean isAdded = projectService.addTeamToProject(projectId,team.getId());
        if(!isAdded)
            throw new Exception("Check project details/Exists Or team Details");
    }

    public List<Team> getTeamsByProject(String projectId){
        return teamRepository.getTeamsByProject(projectId);
    }

    public List<Team> getTeamsByCompany(String projectId) throws Throwable {
        String companyId = projectService.getCompanyId(projectId);
        if(companyId == null)
            return  Collections.EMPTY_LIST;
        List<Team> teamsList = teamRepository.getTeamsByCompany(companyId);
        if(teamsList != null) {
            if(teamsList.size() != 0)
                return teamsList;
            else
                return Collections.EMPTY_LIST;
        }
        else {
            return Collections.EMPTY_LIST;
        }
    }

    public List<Team> getAllTeams(){
        return teamRepository.findAll();
    }

    public void deleteTeam(String projectId, String teamId) throws Throwable {
        Query query1 = new Query();
        query1.addCriteria(Criteria.where("id").is(teamId));
        Team team = mongoTemplate.findOne(query1,Team.class);
        if(team != null) {
            Update update = new Update();
            update.set("isDeleted", true);
            UpdateResult p = mongoTemplate.updateFirst(query1, update, Team.class);
            boolean isDeleted = projectService.deleteTeamFromProject(projectId,team.getId());
            if(!isDeleted)
                throw new TeamNotFound("Team not found");
        }
        else{
           throw new TeamNotFound("Team not found");
        }
    }

    public List<Team> getTeamsByIds(List<String> teamIds) throws TeamNotFound {
        List<Team> teams = teamRepository.getTeamsByIds(teamIds);
        if(teams.size() == 0){
            throw new TeamNotFound("No such teams");
        }
        else{
            return teams;
        }
    }

    public void addMembersToTeam(String teamId,String userId) throws TeamNotFound {
        Team team = teamRepository.getTeamById(teamId);
        if(team == null)
            throw new TeamNotFound("Team does not exist");
        List<String> teamMembers = team.getTeamMembers();
        if(teamMembers == null){
            List<String> members = new ArrayList<String>();
            members.add(userId);
            team.setTeamMembers(members);
        }
        else{
            teamMembers.add(userId);
            team.setTeamMembers(teamMembers);
        }
        teamRepository.save(team);
    }

    public void deleteMultipleTeams(List<String> teamIds){
        teamRepository.deleteAll(teamIds);

    }

    public Team updateTeam(TeamUpdateRequest teamUpdateRequest, String  teamId) throws TeamNotFound {
        Team team =  teamRepository.getTeamById(teamId);
        if(team == null)
            throw new TeamNotFound("No such Team found");
        Helper.copyTeamDetails(team,teamUpdateRequest);
        return teamRepository.save(team);
    }
}
