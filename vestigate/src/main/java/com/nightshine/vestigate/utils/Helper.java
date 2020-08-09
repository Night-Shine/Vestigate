package com.nightshine.vestigate.utils;

import com.nightshine.vestigate.model.board.Board;
import com.nightshine.vestigate.model.project.Project;
import com.nightshine.vestigate.model.task.SubTask;
import com.nightshine.vestigate.model.task.Task;
import com.nightshine.vestigate.model.team.Team;
import com.nightshine.vestigate.model.user.User;
import com.nightshine.vestigate.payload.request.board.BoardUpdateRequest;
import com.nightshine.vestigate.payload.request.project.ProjectUpdateRequest;
import com.nightshine.vestigate.payload.request.task.SubTaskUpdateRequest;
import com.nightshine.vestigate.payload.request.task.TaskUpdateRequest;
import com.nightshine.vestigate.payload.request.team.TeamUpdateRequest;
import com.nightshine.vestigate.payload.request.user.UserUpdateRequest;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Helper {

    public static void copyUserDetails(User user, UserUpdateRequest userRequest) {
        if (userRequest.getName() != null)
            user.setName(userRequest.getName());
        if (userRequest.getEmail() != null)
            user.setEmail(userRequest.getEmail());
        if (userRequest.getUsername() != null)
            user.setUsername(userRequest.getUsername());
        if (userRequest.getPassword() != null)
            user.setPassword(userRequest.getPassword());
        if (userRequest.getPosition() != null)
            user.setPosition(userRequest.getPosition());
        if (userRequest.getRoleType() != null)
            user.setRoleType(userRequest.getRoleType());
        if (userRequest.getImage() != null)
            user.setImage(userRequest.getImage());
    }

    public static void copyTaskDetails(Task task, TaskUpdateRequest taskRequest) {
        if (taskRequest.getTitle() != null)
            task.setTitle(taskRequest.getTitle());
        if (taskRequest.getAssignee() != null)
            task.setAssignee(taskRequest.getAssignee());
        if (taskRequest.getReporter() != null)
            task.setReporter(taskRequest.getReporter());
        if (taskRequest.getDescription() != null)
            task.setDescription(taskRequest.getDescription());
        if (taskRequest.getPriority() != null)
            task.setPriority(taskRequest.getPriority());
        if (taskRequest.getStoryPoints() != null)
            task.setStoryPoints(taskRequest.getStoryPoints());
        if (taskRequest.getSubTask() != null)
            task.setSubTask(taskRequest.getSubTask());
        if (taskRequest.getComments() != null)
            task.setComments(taskRequest.getComments());
    }

    public static void copySubTaskDetails(SubTask subTask, SubTaskUpdateRequest subTaskRequest) {
        if (subTaskRequest.getTitle() != null)
            subTask.setTitle(subTaskRequest.getTitle());
        if (subTaskRequest.getAssignee() != null)
            subTask.setAssignee(subTaskRequest.getAssignee());
        if (subTaskRequest.getReporter() != null)
            subTask.setReporter(subTaskRequest.getReporter());
        if (subTaskRequest.getDescription() != null)
            subTask.setDescription(subTaskRequest.getDescription());
        if (subTaskRequest.getPriority() != null)
            subTask.setPriority(subTaskRequest.getPriority());
        if (subTaskRequest.getStoryPoints() != null)
            subTask.setStoryPoints(subTaskRequest.getStoryPoints());
        if (subTaskRequest.getComments() != null)
            subTask.setComments(subTaskRequest.getComments());
    }

    public static void copyProjectDetails(Project project, ProjectUpdateRequest projectUpdate){
        if(projectUpdate.getProjectName() != null)
            project.setProjectName(projectUpdate.getProjectName());
        if(projectUpdate.getProjectUrl() != null)
            project.setProjectUrl(projectUpdate.getProjectUrl());
        if(projectUpdate.getDescription() != null)
            project.setDescription(projectUpdate.getDescription());
        if(projectUpdate.getImage() != null)
            project.setImage(projectUpdate.getImage());
    }

    public static void copyTeamDetails(Team team, TeamUpdateRequest teamUpdate){
        if(teamUpdate.getDescription() != null)
            team.setDescription(teamUpdate.getDescription());
        if(teamUpdate.getTeamName() != null)
            team.setTeamName(teamUpdate.getTeamName());
    }

    public static void copyBoardDetails(Board board, BoardUpdateRequest boardsUpdate){
        if(boardsUpdate.getAssigned() != null)
            board.setAssigned(boardsUpdate.getAssigned());
    }
}
