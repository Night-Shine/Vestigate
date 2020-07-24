package com.nightshine.vestigate.utils;

import com.mongodb.client.result.UpdateResult;
import com.nightshine.vestigate.model.RoleType;
import com.nightshine.vestigate.model.Task;
import com.nightshine.vestigate.model.User;
import com.nightshine.vestigate.payload.request.TaskUpdateRequest;
import com.nightshine.vestigate.payload.request.UserUpdateRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

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

}
