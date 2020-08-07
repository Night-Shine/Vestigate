package com.nightshine.vestigate.utils;

import com.nightshine.vestigate.model.*;
import com.nightshine.vestigate.payload.request.BoardsUpdateRequest;
import com.nightshine.vestigate.payload.request.ProjectUpdateRequest;
import com.nightshine.vestigate.payload.request.TeamUpdateRequest;
import com.nightshine.vestigate.payload.request.UserUpdateRequest;
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

    public static void copyBoardDetails(Board board, BoardsUpdateRequest boardsUpdate){
        if(boardsUpdate.getAssigned() != null)
            board.setAssigned(boardsUpdate.getAssigned());
    }

}
