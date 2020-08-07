package com.nightshine.vestigate.controller;


import com.nightshine.vestigate.exception.TeamNotFound;
import com.nightshine.vestigate.model.Team;
import com.nightshine.vestigate.payload.request.TeamUpdateRequest;
import com.nightshine.vestigate.service.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/teams")
public class TeamController {

    @Autowired
    private TeamService teamService;

    @PostMapping("/addTeam")
    private ResponseEntity<?> addTeam(@RequestBody Team team, @RequestParam UUID projectId) throws Throwable {
        teamService.saveTeam1(team,projectId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();

    }

    @GetMapping("/getProjectTeams")
    private ResponseEntity<List<Team>> getTeamsOfProject(@RequestParam UUID projectId ){
        return ResponseEntity.ok(teamService.getTeamsByProject(projectId));
    }

    @GetMapping("/getCompanyTeams")
    private ResponseEntity<List<Team>> getTeamsOfCompany(@RequestParam UUID projectId) throws Throwable {
        return ResponseEntity.ok(teamService.getTeamsByCompany(projectId));
    }

    @GetMapping("/getAllTeams")
    private ResponseEntity<List<Team>> getAllTeams(){
        return ResponseEntity.ok(teamService.getAllTeams());
    }

    @DeleteMapping("/deleteTeam")
    private ResponseEntity<?> deleteTeam(@RequestParam UUID projectId,@RequestParam UUID teamId) throws Throwable {
         teamService.deleteTeam(projectId,teamId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @DeleteMapping("/deleteMultipleTeams")
    public ResponseEntity<?> deleteMultipleTeams(@Valid @RequestBody List<UUID> ids,@RequestParam UUID projectId) throws Throwable {
        teamService.deleteMultipleTeams(ids,projectId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PutMapping("/updateTeam/{teamId}")
    public ResponseEntity<Team> updateTeams(@Valid @RequestBody TeamUpdateRequest teamUpdateRequest, @PathVariable UUID teamId) throws TeamNotFound {
        Team team = teamService.updateTeam(teamUpdateRequest, teamId);
        return new ResponseEntity<>(team, HttpStatus.ACCEPTED);
    }
}
