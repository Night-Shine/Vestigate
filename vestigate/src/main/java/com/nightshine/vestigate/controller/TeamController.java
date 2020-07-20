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

@RestController
@RequestMapping("/teams")
public class TeamController {

    @Autowired
    private TeamService teamService;

    @PostMapping("/addTeam")
    private ResponseEntity<?> addTeam(@RequestBody Team team, @RequestParam String projectId) throws Throwable {
        teamService.saveTeam1(team,projectId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();

    }

    @GetMapping("/getProjectTeams")
    private ResponseEntity<List<Team>> getTeamsOfProject(@RequestParam String projectId ){
        return ResponseEntity.ok(teamService.getTeamsByProject(projectId));
    }

    @GetMapping("/getCompanyTeams")
    private ResponseEntity<List<Team>> getTeamsOfCompany(@RequestParam String projectId) throws Throwable {
        return ResponseEntity.ok(teamService.getTeamsByCompany(projectId));
    }

    @GetMapping("/getAllTeams")
    private ResponseEntity<List<Team>> getAllTeams(){
        return ResponseEntity.ok(teamService.getAllTeams());
    }

    @DeleteMapping("/deleteTeam")
    private ResponseEntity<?> deleteTeam(@RequestParam String projectId,@RequestParam String teamId) throws Throwable {
         teamService.deleteTeam(projectId,teamId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @DeleteMapping("/deleteMultipleTeams")
    public ResponseEntity<?> deleteMultipleTeams(@Valid @RequestBody List<String> ids) {
        teamService.deleteMultipleTeams(ids);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PutMapping("/updateAllTeams/{teamId}")
    public ResponseEntity<Team> updateTeams(@Valid @RequestBody TeamUpdateRequest teamUpdateRequest, @PathVariable String teamId) throws TeamNotFound {
        Team team = teamService.updateTeam(teamUpdateRequest, teamId);
        return new ResponseEntity<>(team, HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/deleteMultipleTeams")
    public ResponseEntity<?> deleteMultipleTeams(@Valid @RequestBody List<String> ids) {
        teamService.deleteMultipleTeams(ids);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PutMapping("/updateAllTeams/{teamId}")
    public ResponseEntity<Team> updateTeams(@Valid @RequestBody TeamUpdateRequest teamUpdateRequest, @PathVariable String teamId) throws TeamNotFound {
        Team team = teamService.updateTeam(teamUpdateRequest, teamId);
        return new ResponseEntity<>(team, HttpStatus.ACCEPTED);
    }
}
