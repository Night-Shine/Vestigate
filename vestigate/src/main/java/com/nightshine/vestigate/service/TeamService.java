package com.nightshine.vestigate.service;



import com.nightshine.vestigate.exception.CompanyNotFound;
import com.nightshine.vestigate.exception.TeamNotFound;
import com.nightshine.vestigate.model.Team;
import com.nightshine.vestigate.payload.request.TeamUpdateRequest;
import com.nightshine.vestigate.repository.teams.TeamRepository;
import com.nightshine.vestigate.utils.Helper;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;

@Transactional
@Service
public class TeamService {

    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private ProjectService projectService;



    public void saveTeam1(Team team, UUID projectId) throws Throwable {
        team.setCompanyId(projectService.getCompanyId(projectId));
        team.setProjectId(projectId);

        Team savedTeam = teamRepository.save(team);
        UUID companyId = savedTeam.getCompanyId();
        if(companyId == null)
            throw new CompanyNotFound("Company does not exists");
        boolean isAdded = projectService.addTeamToProject(projectId,team.getId());
        if(!isAdded)
            throw new Exception("Check project details/Exists Or team Details");
    }

    public List<Team> getTeamsByProject(UUID projectId){
        return teamRepository.getTeamsByProject(projectId);
    }

    public List<Team> getTeamsByCompany(UUID projectId) throws Throwable {
        UUID companyId = projectService.getCompanyId(projectId);
        final List<Team> emptyList = new ArrayList<>();
        if(companyId == null)
            return emptyList;
        List<Team> teamsList = teamRepository.getTeamsByCompany(companyId);
        if(teamsList != null) {
            if(teamsList.size() != 0)
                return teamsList;
            else
                return emptyList;
        }
        else {
            return emptyList;
        }
    }

    public List<Team> getAllTeams(){
        return teamRepository.findAll();
    }

    public void deleteTeam(UUID projectId, UUID teamId) throws Throwable {
        Optional<Team> team = teamRepository.findById(teamId);
        if(team.isPresent()) {
            teamRepository.deleteById(teamId);
            boolean isDeleted = projectService.deleteTeamFromProject(projectId,teamId);
            if(!isDeleted)
                throw new TeamNotFound("Team not found");
        }
        else{
           throw new TeamNotFound("Team not found");
        }
    }

    public List<Team> getTeamsByIds(List<UUID> teamIds) throws TeamNotFound {
        List<Team> teams = teamRepository.getTeamsByIds(teamIds);
        if(teams.size() == 0){
            throw new TeamNotFound("No such teams");
        }
        else{
            return teams;
        }
    }

    public void addMembersToTeam(UUID teamId,UUID userId) throws TeamNotFound {
        Optional<Team> team = teamRepository.findById(teamId);
        if(!team.isPresent())
            throw new TeamNotFound("Team does not exist");
        Team t = team.get();
        List<UUID> teamMembers = t.getTeamMembers();
        if(teamMembers == null){
            List<UUID> members = new ArrayList<UUID>();
            members.add(userId);
            t.setTeamMembers(members);
        }
        else{
            teamMembers.add(userId);
            t.setTeamMembers(teamMembers);
        }
        teamRepository.save(t);
    }

    public void deleteMultipleTeams(List<UUID> teamIds,UUID projectId) throws Throwable {

        teamRepository.deleteAll(teamIds);
        for(UUID id : teamIds){
            projectService.deleteTeamFromProject(projectId,id);
        }

    }

    public Team updateTeam(TeamUpdateRequest teamUpdateRequest, UUID  teamId) throws TeamNotFound {
        Optional<Team> team =  teamRepository.findById(teamId);
        if(!team.isPresent())
            throw new TeamNotFound("No such Team found");
        Team t = team.get();
        Helper.copyTeamDetails(t,teamUpdateRequest);
        return teamRepository.save(t);
    }
}
