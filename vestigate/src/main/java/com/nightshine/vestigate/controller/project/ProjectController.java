package com.nightshine.vestigate.controller.project;


import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.nightshine.vestigate.exception.project.ProjectNotFound;
import com.nightshine.vestigate.exception.team.TeamNotFound;
import com.nightshine.vestigate.model.board.Board;
import com.nightshine.vestigate.model.project.Project;
import com.nightshine.vestigate.model.team.Team;
import com.nightshine.vestigate.payload.request.project.ProjectUpdateRequest;
import com.nightshine.vestigate.service.project.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/projects")
public class ProjectController {
    @Autowired
    private ProjectService projectService;

    @JsonAnyGetter
    @PostMapping("/addProject")
    private ResponseEntity<?> addProject(@RequestBody Project project) throws Exception {
         projectService.saveProject(project);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();

    }

    @GetMapping("/getCompanyProjects")
    private ResponseEntity<List<Project>> getProjectsOfCompany(@RequestParam("Id") UUID id){

        return ResponseEntity.ok(projectService.getProjectsByCompany(id)) ;
    }

    @DeleteMapping("/deleteProject")
    private ResponseEntity<?> deleteProject(@RequestParam("id") UUID id) throws Exception {
         projectService.deleteProjectById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();

    }




    @GetMapping("/getTeamsOfProject")
    private ResponseEntity<List<Team>> getAllTeamsOfProject(@RequestParam UUID projectId) throws TeamNotFound, ProjectNotFound {
        return ResponseEntity.ok(projectService.getTeamsOfProject(projectId));
    }

    @GetMapping("/getBoardsOfProject")
    private ResponseEntity<List<Board>> getAllBoardsOfProject(@RequestParam UUID projectId) throws Exception {
        return ResponseEntity.ok(projectService.getBoardsOfProject(projectId));
    }

    @DeleteMapping("/deleteMultipleProjects")
    public ResponseEntity<?> deleteMultipleProjects(@RequestBody List<UUID> ids) {
        projectService.deleteMultipleProjects(ids);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PutMapping("/updateProject/{projectId}")
    public ResponseEntity<Project> updateProjects(@Valid @RequestBody ProjectUpdateRequest projectUpdateRequest, @PathVariable UUID projectId) throws ProjectNotFound {
        Project project = projectService.updateProjects(projectUpdateRequest, projectId);
        return new ResponseEntity<>(project, HttpStatus.ACCEPTED);
    }

    @GetMapping("/getProjectInfo")
    public ResponseEntity<Project> getProjectInfo(@RequestParam UUID projectId) throws ProjectNotFound {
        return ResponseEntity.ok(projectService.getProject(projectId));
    }
}
