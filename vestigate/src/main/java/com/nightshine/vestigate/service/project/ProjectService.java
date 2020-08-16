package com.nightshine.vestigate.service.project;


import com.fasterxml.jackson.annotation.JsonGetter;
import com.nightshine.vestigate.exception.company.CompanyNotFound;
import com.nightshine.vestigate.exception.project.ProjectNotFound;
import com.nightshine.vestigate.exception.team.TeamNotFound;
import com.nightshine.vestigate.model.board.Board;
import com.nightshine.vestigate.model.company.Company;
import com.nightshine.vestigate.model.project.Project;
import com.nightshine.vestigate.payload.request.project.ProjectUpdateRequest;
import com.nightshine.vestigate.payload.response.ApiResponse;
import com.nightshine.vestigate.repository.company.CompanyRepository;
import com.nightshine.vestigate.repository.project.ProjectRepository;
import com.nightshine.vestigate.service.company.CompanyService;
import com.nightshine.vestigate.service.team.TeamService;
import com.nightshine.vestigate.service.board.BoardService;
import com.nightshine.vestigate.utils.Helper;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Transactional
@Service
public class ProjectService {

    @Autowired
    private ProjectRepository projectRepo;

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private TeamService teamService;

    @Autowired
    private BoardService boardService;

    @Autowired
    private CompanyService companyService;


    @JsonGetter("message")
    public ResponseEntity<?> saveProject(Project project,UUID companyId) throws ProjectNotFound,Exception {
        Optional<Company> company = companyRepository.findById(companyId);
        if(!company.isPresent())
            return new ResponseEntity(new ApiResponse(false, "Company does not exists!"),
                    HttpStatus.BAD_REQUEST);
        else {
            Project isExists = projectRepo.findByProjectName(project.getProjectName(), companyId);
            project.setCompanyId(companyId);
            if (isExists != null)
                return new ResponseEntity(new ApiResponse(false, "Project already exists!"),
                        HttpStatus.BAD_REQUEST);
            Project savedProject = projectRepo.save(project);
            ResponseEntity status = companyService.addProjectToCompany(companyId,savedProject.getId());

            if (savedProject == null)
                return new ResponseEntity(new ApiResponse(false, "Something went wrong!"),
                        HttpStatus.BAD_REQUEST);
            else
                return new ResponseEntity(project, HttpStatus.CREATED);

        }
    }

    public ResponseEntity<?> getProjectsByCompany(UUID companyId){
        Optional<Company> company = companyRepository.findById(companyId);
        if(!company.isPresent()){
            return new ResponseEntity(new ApiResponse(false, "Company does not exist!"),
                    HttpStatus.BAD_REQUEST);
        }
        List<Project> companyProjects = projectRepo.getProjectsByCompanyId(companyId);
        return new ResponseEntity(companyProjects, HttpStatus.OK);
    }

    public ResponseEntity<?> deleteProjectById(UUID id) throws Exception {
        Optional<Project> project = projectRepo.findById(id);
        System.out.println(project.isPresent());
        if(project.isPresent()) {
            Project p = project.get();
                if (!p.getIsDeleted()) {
                    projectRepo.deleteById(p.getId());
                    return new ResponseEntity(project.get().getIsDeleted(), HttpStatus.CREATED);
                }
                else
                    return new ResponseEntity(new ApiResponse(false, "Project does not  exists!"),
                            HttpStatus.BAD_REQUEST);
        }
        else {
            return new ResponseEntity(new ApiResponse(false, "Project does not  exists!"),
                    HttpStatus.BAD_REQUEST);
        }
    }


    public ResponseEntity<?> getCompanyId(UUID id) throws Exception {
        Optional<Project> project = projectRepo.findById(id);
        if(project.isPresent()) {
            Project p = project.get();
            if (p.getProjectName() != "") {
                if (!p.getIsDeleted() ) {
                    UUID cId = p.getCompanyId();
                    if(cId != null)
                        return new ResponseEntity(cId, HttpStatus.CREATED);
                    else
                        return new ResponseEntity(new ApiResponse(false, "Company  not  found!"),
                                HttpStatus.BAD_REQUEST);
                }
            }
        }
        return new ResponseEntity(new ApiResponse(false, "Project does not  exists!"),
                HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<?> addTeamToProject(UUID projectId, UUID teamId) throws Exception {
        Optional<Project> project = projectRepo.findById(projectId);
        if(project.isPresent()){
            Project p = project.get();
            List<UUID> teamIds = p.getTeamId();
            if(teamIds == null){
                List<UUID> tIds = new ArrayList<>();
                tIds.add(teamId);
                p.setTeamId(tIds);
            }
            else {
                teamIds.add(teamId);
                p.setTeamId(teamIds);
            }
            projectRepo.save(p);
            return new ResponseEntity(true, HttpStatus.CREATED);
        }
        else{
            return new ResponseEntity(new ApiResponse(false, "Project does not  exists!"),
                    HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<?>  deleteTeamFromProject(UUID projectId, UUID teamId) throws Throwable {
        Optional<Project> project = projectRepo.findById(projectId);
        if(project.isPresent()){
            Project p = project.get();
            List<UUID> teamIds =  p.getTeamId();
            int index = teamIds.indexOf(teamId);
            if(teamIds.size()==0){
                return new ResponseEntity(new ApiResponse(false, "Project does not  have teams!"),
                        HttpStatus.BAD_REQUEST);
            }
            if(index > -1){
                teamIds.remove(index);
                p.setTeamId(teamIds);
                projectRepo.save(p);
                return new ResponseEntity(true, HttpStatus.CREATED);
            }
            else{
                return new ResponseEntity(new ApiResponse(false, "Cannot find team!"),
                        HttpStatus.BAD_REQUEST);
            }

        }
        else{
            return new ResponseEntity(new ApiResponse(false, "Project does not  exists!"),
                    HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<?>  getTeamsOfProject(UUID projectId) throws TeamNotFound, ProjectNotFound {
        Optional<Project> project = projectRepo.findById(projectId);
        if(project.isPresent()) {
            return new ResponseEntity(teamService.getTeamsByIds(project.get().getTeamId()).getBody(), HttpStatus.OK);
        }
        else
            return new ResponseEntity(new ApiResponse(false, "Project does not  exists!"),
                    HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<?>  addBoardToProject(UUID projectId, UUID boardId) throws Exception {
        Optional<Project> project = projectRepo.findById(projectId);
        if(project.isPresent()){
            Project p = project.get();
            List<UUID> boardIds =  p.getBoardsId();
            if(boardIds == null){
                List<UUID> boards = new ArrayList<>();
                boards.add(boardId);
                p.setBoardsId(boards);
            }
            else {
                boardIds.add(boardId);
                p.setBoardsId(boardIds);
            }
            projectRepo.save(p);
            return new ResponseEntity(true, HttpStatus.CREATED);
        }
        else{
            return new ResponseEntity(new ApiResponse(false, "Project does not  exists!"),
                    HttpStatus.BAD_REQUEST);
        }
    }

    public  ResponseEntity<?> deleteBoardFromProject(UUID projectId, UUID boardId) throws Exception {
        Optional<Project> project = projectRepo.findById(projectId);
        if(project.isPresent()){
            Project p = project.get();
            List<UUID> boardIds = p.getBoardsId();
            int index = boardIds.indexOf(boardId);
            if(index > -1) {
                boardIds.remove(index);

                p.setBoardsId(boardIds);
                projectRepo.save(p);
                return new ResponseEntity(true, HttpStatus.CREATED);
            }
            else{
                return new ResponseEntity(new ApiResponse(false, "Board does not  exists!"),
                        HttpStatus.BAD_REQUEST);
            }

        }
        else{
            return new ResponseEntity(new ApiResponse(false, "Project does not  exists!"),
                    HttpStatus.BAD_REQUEST);
        }
    }

    public  ResponseEntity<?> getBoardsOfProject(UUID projectId)throws Exception {
        Optional<Project> project = projectRepo.findById(projectId);
        if(project.isPresent()) {
            Project p = project.get();
           return boardService.getBoardsByIds(p.getBoardsId());

        }else{
            return new ResponseEntity(new ApiResponse(false, "Project does not  exists!"),
                    HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<?>  deleteMultipleProjects(List<UUID> projectIds){
        for(UUID bid:projectIds) {
            Optional<Project> project = projectRepo.findById(bid);
            if(!project.isPresent()) {
                return new ResponseEntity(new ApiResponse(false, "Projects does not exists!"),
                        HttpStatus.BAD_REQUEST);
            }
        }
        projectRepo.deleteAll(projectIds);
        return new ResponseEntity(new ApiResponse(true, "Projects deleted successfully"),
                HttpStatus.OK);
    }

    public ResponseEntity<?> updateProjects(ProjectUpdateRequest projectUpdateRequest, UUID  projectsId) throws ProjectNotFound {
        Optional<Project> project =  projectRepo.findById(projectsId);
        if(!project.isPresent())
            return new ResponseEntity(new ApiResponse(false, "Project does not  exists!"),
                    HttpStatus.BAD_REQUEST);
        Project p = project.get();
        Helper.copyProjectDetails(p,projectUpdateRequest);
        return new ResponseEntity(projectRepo.save(p), HttpStatus.CREATED);
    }

    public ResponseEntity<?> getProject(UUID projectId) throws ProjectNotFound {
        Optional<Project> project = projectRepo.findById(projectId);
        if(project.isPresent())
            return new ResponseEntity(project.get(), HttpStatus.CREATED);
        else
            return new ResponseEntity(new ApiResponse(false, "Project does not  exists!"),
                    HttpStatus.BAD_REQUEST);
    }
}
