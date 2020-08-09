package com.nightshine.vestigate.service.project;


import com.fasterxml.jackson.annotation.JsonGetter;
import com.nightshine.vestigate.exception.board.BoardNotFound;
import com.nightshine.vestigate.exception.project.ProjectNotFound;
import com.nightshine.vestigate.exception.team.TeamNotFound;
import com.nightshine.vestigate.model.board.Board;
import com.nightshine.vestigate.model.project.Project;
import com.nightshine.vestigate.model.team.Team;
import com.nightshine.vestigate.payload.request.project.ProjectUpdateRequest;
import com.nightshine.vestigate.repository.project.ProjectRepository;
import com.nightshine.vestigate.service.team.TeamService;
import com.nightshine.vestigate.service.board.BoardService;
import com.nightshine.vestigate.utils.Helper;
import org.springframework.beans.factory.annotation.Autowired;

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
    private TeamService teamService;

    @Autowired
    private BoardService boardService;





    @JsonGetter("message")
    public void saveProject(Project project) throws ProjectNotFound,Exception {
        System.out.println(project.getDescription());
        Project isExists =projectRepo.findByProjectName(project.getProjectName());
        if(isExists != null)
            throw new ProjectNotFound("Project already exists");
        Project savedProject = projectRepo.save(project);
        if(savedProject == null)
            throw new Exception("Something went wrong while saving");
    }

    public List<Project> getProjectsByCompany(UUID companyId){
        List<Project> companyProjects = projectRepo.getProjectsByCompanyId(companyId);
        return companyProjects;
    }

    public void deleteProjectById(UUID id) throws Exception {
        Optional<Project> project = projectRepo.findById(id);
        if(project.isPresent()) {
            Project p = project.get();
            if (p.getProjectName() != "") {
                if (!p.getIsDeleted()) {
                    projectRepo.deleteById(p.getId());
                }
            }
        }
        throw new Exception("Project Does not Exist");
    }


    public UUID getCompanyId(UUID id) throws Exception {
        Optional<Project> project = projectRepo.findById(id);
        if(project.isPresent()) {
            Project p = project.get();
            if (p.getProjectName() != "") {
                if (!p.getIsDeleted() ) {
                    return p.getCompanyId();
                }
            }
        }
        throw new Exception("Project Does not Exist");
//        return null;
    }

    public boolean addTeamToProject(UUID projectId, UUID teamId) throws Exception {
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
            return true;
        }
        else{
            throw new Exception("Project Does not Exist");
//            return false;
        }
    }

    public boolean deleteTeamFromProject(UUID projectId, UUID teamId) throws Throwable {
        Optional<Project> project = projectRepo.findById(projectId);
        if(project.isPresent()){
            Project p = project.get();
            List<UUID> teamIds =  p.getTeamId();
            int index = teamIds.indexOf(teamId);
            if(teamIds.size()==0){
                throw new Exception("Project does not have any Team");
            }
            if(index > -1){
                teamIds.remove(index);
                p.setTeamId(teamIds);
                projectRepo.save(p);
                return true;
            }
            else{
                throw new Exception("Cannot find team ");
            }

        }
        else{
            throw new Throwable("Project Does not Exist");
        }
    }

    public List<Team> getTeamsOfProject(UUID projectId) throws TeamNotFound, ProjectNotFound {
        Optional<Project> project = projectRepo.findById(projectId);
        if(project.isPresent())
            return teamService.getTeamsByIds(project.get().getTeamId());
        else
            throw new ProjectNotFound("Project not found");
    }

    public List<Project> getAll(){
        return projectRepo.findAll();
    }

    public boolean addBoardToProject(UUID projectId, UUID boardId) throws Exception {
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
            return true;
        }
        else{
            throw new Exception("Project Does not Exist");
        }
    }

    public boolean deleteBoardFromProject(UUID projectId, UUID boardId) throws Exception {
        Optional<Project> project = projectRepo.findById(projectId);
        if(project.isPresent()){
            Project p = project.get();
            List<UUID> boardIds = p.getBoardsId();
            int index = boardIds.indexOf(boardId);
            if(index > -1) {
                boardIds.remove(index);

                p.setBoardsId(boardIds);
                projectRepo.save(p);
                return true;
            }
            else{
                throw new Exception("Cannot find board ");
            }

        }
        else{
            throw new Exception("Project Does not Exist");
        }
    }

    public List<Board> getBoardsOfProject(UUID projectId)throws Exception {
        Optional<Project> project = projectRepo.findById(projectId);
        if(project.isPresent()) {
            Project p = project.get();
            List<Board> boardList = boardService.getBoardsByIds(p.getBoardsId());
            if (boardList != null)
                return boardList;
            else
                throw new BoardNotFound("There are no such boards");
        }else{
            throw new ProjectNotFound("Project not found");
        }
    }

    public String deleteMultipleProjects(List<UUID> projectIds){
        projectRepo.deleteAll(projectIds);
        return "Deleted Multiple Project";
    }

    public Project updateProjects(ProjectUpdateRequest projectUpdateRequest, UUID  projectsId) throws ProjectNotFound {
        Optional<Project> project =  projectRepo.findById(projectsId);
        if(!project.isPresent())
            throw new ProjectNotFound("No such Project found");
        Project p = project.get();
        Helper.copyProjectDetails(p,projectUpdateRequest);
        return projectRepo.save(p);
    }

    public Project getProject(UUID projectId) throws ProjectNotFound {
        Optional<Project> project = projectRepo.findById(projectId);
        if(project.isPresent())
            return  project.get();
        else
            throw new ProjectNotFound("Project not found");
    }
}
