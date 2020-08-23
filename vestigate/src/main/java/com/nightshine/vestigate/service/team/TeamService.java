package com.nightshine.vestigate.service.team;



import com.nightshine.vestigate.exception.company.CompanyNotFound;
import com.nightshine.vestigate.exception.project.ProjectNotFound;
import com.nightshine.vestigate.exception.team.TeamNotFound;
import com.nightshine.vestigate.model.board.Board;
import com.nightshine.vestigate.model.company.Company;
import com.nightshine.vestigate.model.project.Project;
import com.nightshine.vestigate.model.team.Team;
import com.nightshine.vestigate.payload.request.team.TeamUpdateRequest;
import com.nightshine.vestigate.payload.response.ApiResponse;
import com.nightshine.vestigate.repository.project.ProjectRepository;
import com.nightshine.vestigate.repository.team.TeamRepository;
import com.nightshine.vestigate.service.project.ProjectService;
import com.nightshine.vestigate.utils.Helper;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @Autowired
    private ProjectRepository projectRepo;


    public ResponseEntity<?> saveTeam(Team team, UUID projectId) throws Throwable,Exception {
        Optional<Project> project = projectRepo.findById(projectId);
        if(!project.isPresent()){
            return new ResponseEntity(new ApiResponse(false, "Project does not exists!"),
                    HttpStatus.BAD_REQUEST);
        }
        else {
            if(projectService.getCompanyId(projectId) == null)
                return new ResponseEntity(new ApiResponse(false, "Company does not exists!"),
                        HttpStatus.BAD_REQUEST);
            Team isExists = teamRepository.findByTeamName(team.getTeamName(),projectId);
            if (isExists != null)
                return new ResponseEntity(new ApiResponse(false, "Team already exists!"),
                        HttpStatus.BAD_REQUEST);
            UUID cId = (UUID) projectService.getCompanyId(projectId).getBody();
            System.out.println("obj = "+cId);
            team.setCompanyId(cId);
            team.setProjectId(projectId);

            Team savedTeam = teamRepository.save(team);
            ResponseEntity<?> isAdded = projectService.addTeamToProject(projectId, savedTeam.getId());
            return new ResponseEntity(savedTeam, HttpStatus.CREATED);
        }
    }

    public ResponseEntity<?> getTeamsByProject(UUID projectId){
        Optional<Project> project = projectRepo.findById(projectId);
        if(!project.isPresent()){
            return new ResponseEntity(new ApiResponse(false, "Project does not exist!"),
                    HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity(teamRepository.getTeamsByProject(projectId), HttpStatus.OK);
    }

    public ResponseEntity<?> getTeamsByCompany(UUID projectId) throws Throwable {
        Optional<Project> project = projectRepo.findById(projectId);
        if(!project.isPresent()){
            return new ResponseEntity(new ApiResponse(false, "Project does not exist!"),
                    HttpStatus.BAD_REQUEST);
        }
        UUID companyId = (UUID) projectService.getCompanyId(projectId).getBody();
        final List<Team> emptyList = new ArrayList<>();
        if(companyId == null)
            return new ResponseEntity(emptyList, HttpStatus.OK);
        List<Team> teamsList = teamRepository.getTeamsByCompany(companyId);
        if(teamsList != null) {
            if(teamsList.size() != 0)
                return new ResponseEntity(teamsList, HttpStatus.OK);
            else
                return new ResponseEntity(emptyList, HttpStatus.OK);
        }
        else {
            return new ResponseEntity(emptyList, HttpStatus.OK);
        }
    }

    public List<Team> getAllTeams(){
        return teamRepository.findAll();
    }

    public ResponseEntity<?> deleteTeam(UUID projectId, UUID teamId) throws Throwable {
        Optional<Team> team = teamRepository.findById(teamId);
        if(team.isPresent()) {
            teamRepository.deleteById(teamId);
            ResponseEntity<?> isDeleted = projectService.deleteTeamFromProject(projectId,teamId);

            if(!isDeleted.getBody().toString().equals("true"))
                return new ResponseEntity(new ApiResponse(false, "Team does not exists!"),
                        HttpStatus.BAD_REQUEST);
            else
                return new ResponseEntity(true, HttpStatus.CREATED);
        }
        else{
            return new ResponseEntity(new ApiResponse(false, "Team does not exists!"),
                    HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<?> getTeamsByIds(List<UUID> teamIds) throws TeamNotFound {
        List<Team> teams = teamRepository.getTeamsByIds(teamIds);
        if(teams.size() == 0){
            return new ResponseEntity(new ApiResponse(false, "Teams does not exists!"),
                    HttpStatus.BAD_REQUEST);
        }
        else{
            return new ResponseEntity(teams, HttpStatus.CREATED);
        }
    }

    public ResponseEntity<?>  addMembersToTeam(UUID teamId,UUID userId) throws TeamNotFound {
        Optional<Team> team = teamRepository.findById(teamId);
        if(!team.isPresent())
            return new ResponseEntity(new ApiResponse(false, "Teams does not exists!"),
                    HttpStatus.BAD_REQUEST);
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
        Team savedTeam = teamRepository.save(t);

        return new ResponseEntity(savedTeam, HttpStatus.CREATED);
    }

    public ResponseEntity<?> deleteMultipleTeams(List<UUID> teamIds,UUID projectId) throws Throwable {
        Optional<Project> project = projectRepo.findById(projectId);
        if(!project.isPresent())
            return new ResponseEntity(new ApiResponse(false, "Project does not exists!"),
                    HttpStatus.BAD_REQUEST);

        List<Boolean> isExists =new ArrayList<Boolean>(Arrays.asList(new Boolean[teamIds.size()]));
        Collections.fill(isExists, Boolean.TRUE);

        teamIds.forEach(bid -> {
            Optional<Team> team = teamRepository.findById(bid);
            if (!team.isPresent()) {
                int index = teamIds.indexOf(bid);
                isExists.set(index,false);
            }
        });

        Boolean f = false;
        if(isExists.indexOf(f) > 0) {
            String msg =  (isExists.indexOf(f)+1) + " Team does not exists! " ;
            return new ResponseEntity(new ApiResponse(false, msg),
                    HttpStatus.BAD_REQUEST);
        }
        teamRepository.deleteAll(teamIds);
        for(UUID id : teamIds){
            projectService.deleteTeamFromProject(projectId,id);
        }
        return new ResponseEntity(new ApiResponse(true, "Teams deleted successfully"),
                HttpStatus.BAD_REQUEST);

    }

    public ResponseEntity<?>  updateTeam(TeamUpdateRequest teamUpdateRequest, UUID  teamId) throws TeamNotFound {
        Optional<Team> team =  teamRepository.findById(teamId);
        if(!team.isPresent())
            return new ResponseEntity(new ApiResponse(false, "Teams does not exists!"),
                    HttpStatus.BAD_REQUEST);
        Team t = team.get();
        Helper.copyTeamDetails(t,teamUpdateRequest);
        Team savedTeam = teamRepository.save(t);
        return new ResponseEntity(savedTeam, HttpStatus.CREATED);
    }
}
