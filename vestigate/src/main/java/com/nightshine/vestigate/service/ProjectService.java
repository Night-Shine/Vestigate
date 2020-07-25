package com.nightshine.vestigate.service;


import com.fasterxml.jackson.annotation.JsonGetter;
import com.mongodb.client.result.UpdateResult;
import com.nightshine.vestigate.exception.BoardNotFound;
import com.nightshine.vestigate.exception.ProjectNotFound;
import com.nightshine.vestigate.exception.TeamNotFound;
import com.nightshine.vestigate.model.Board;
import com.nightshine.vestigate.model.Project;
import com.nightshine.vestigate.model.Team;
import com.nightshine.vestigate.payload.request.ProjectUpdateRequest;
import com.nightshine.vestigate.repository.projects.ProjectsRepository;
import com.nightshine.vestigate.utils.Helper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.mongodb.core.MongoTemplate;


@Service
public class ProjectService {

    @Autowired
    private ProjectsRepository projectRepo;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private TeamService teamService;

    @Autowired
    private BoardsService boardsService;



    @JsonGetter("message")
    public void saveProject(Project project) throws ProjectNotFound {

        String message;
        Project isExists =projectRepo.findByProjectName(project.getProjectName());
        if(isExists != null)
            throw new ProjectNotFound("Project already exists");
        Project savedProject = projectRepo.save(project);

    }

    public List<Project> getProjectsByCompany(String companyId){
        List<Project> companyProjects = projectRepo.getProjectsByCompanyId(companyId);
        return companyProjects;
    }

    public void deleteProjectById(String id) throws Exception {
        Query query1 = new Query();
        query1.addCriteria(Criteria.where("id").is(id));
        Project project = mongoTemplate.findOne(query1, Project.class);
        if( project.getProjectName() != "") {
            if(!project.getIsDeleted() ) {
                Update update = new Update();
                update.set("isDeleted", true);
                UpdateResult p = mongoTemplate.updateFirst(query1, update, Project.class);
            }
        }
        throw new Exception("Project Does not Exist");
    }


    public String getCompanyId(String id) throws Exception {
        Query query1 = new Query();
        query1.addCriteria(Criteria.where("id").is(id));
        Project project = mongoTemplate.findOne(query1, Project.class);

        if(project != null ) {
            if (project.getProjectName() != "") {
                if (!project.getIsDeleted() ) {
                    return project.getCompanyId();
                }
            }
        }
        throw new Exception("Project Does not Exist");
//        return null;
    }

    public boolean addTeamToProject(String projectId, String teamId) throws Exception {
        Project project = projectRepo.findByProjectId(projectId);
        if(project != null){
            List<String> teamIds = project.getTeamId();
            if(teamIds == null){
                List<String> tIds = new ArrayList<>();
                tIds.add(teamId);
                project.setTeamId(tIds);
            }
            else {
                teamIds.add(teamId);
                project.setTeamId(teamIds);
            }
            projectRepo.save(project);
            return true;
        }
        else{
            throw new Exception("Project Does not Exist");
//            return false;
        }
    }

    public boolean deleteTeamFromProject(String projectId, String teamId) throws Throwable {
        Project project = projectRepo.findByProjectId(projectId);
        if(project != null){
            ArrayList<String> teamIds = (ArrayList<String>) project.getTeamId();
            int index = teamIds.indexOf(teamId);
            if(teamIds.size()==0){
                throw new Exception("Project does not have any Team");
            }
            if(index > -1){
                teamIds.remove(index);
                project.setTeamId(teamIds);
                projectRepo.save(project);
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

    public List<Team> getTeamsOfProject(String projectId) throws TeamNotFound, ProjectNotFound {
        Project project = projectRepo.findByProjectId(projectId);
        if(project != null)
            return teamService.getTeamsByIds(project.getTeamId());
        else
            throw new ProjectNotFound("Project not found");
    }

    public List<Project> getAll(){
        return projectRepo.findAll();
    }

    public boolean addBoardToProject(String projectId, String boardId) throws Exception {
        Project project = projectRepo.findByProjectId(projectId);
        if(project != null){
            ArrayList<String> boardIds = (ArrayList<String>) project.getBoardsId();
            if(boardIds == null){
                List<String> boards = new ArrayList<>();
                boards.add(boardId);
                project.setBoardsId(boards);
            }
            else {
                boardIds.add(boardId);
                project.setBoardsId(boardIds);
            }
            projectRepo.save(project);
            return true;
        }
        else{
            throw new Exception("Project Does not Exist");
        }
    }

    public boolean deleteBoardFromProject(String projectId, String boardId) throws Exception {
        Project project = projectRepo.findByProjectId(projectId);
        if(project != null){
            ArrayList<String> boardIds = (ArrayList<String>) project.getBoardsId();
            int index = boardIds.indexOf(boardId);
            if(index > -1) {
                boardIds.remove(index);
                project.setBoardsId(boardIds);
                projectRepo.save(project);
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

    public List<Board> getBoardsOfProject(String projectId)throws Exception {
        Project project = projectRepo.findByProjectId(projectId);
        List<Board> boardList = boardsService.getBoardsByIds(project.getBoardsId());
        if(boardList != null)
            return boardList;
        else
            throw new BoardNotFound("There are no such boards");
    }

    public String deleteMultipleProjects(List<String> projectIds){
        projectRepo.deleteAll(projectIds);
        return "Deleted Multiple Project";
    }

    public Project updateProjects(ProjectUpdateRequest projectUpdateRequest, String  projectsId) throws ProjectNotFound {
        Project project =  projectRepo.findByProjectId(projectsId);
        if(project == null)
            throw new ProjectNotFound("No such Project found");
        Helper.copyProjectDetails(project,projectUpdateRequest);
        return projectRepo.save(project);
    }

    public Project getProject(String projectId) throws ProjectNotFound {
        Project project = projectRepo.findByProjectId(projectId);
        if(project != null)
            return  project;
        else
            throw new ProjectNotFound("Project not found");
    }
}
