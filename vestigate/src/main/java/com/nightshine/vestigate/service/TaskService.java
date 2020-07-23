package com.nightshine.vestigate.service;

import com.nightshine.vestigate.exception.TaskNotFound;
import com.nightshine.vestigate.model.Task;
import com.nightshine.vestigate.payload.request.TaskUpdateRequest;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;


public interface TaskService {
	
	Task addTask(Task task);
	ResponseEntity deleteTask(String taskId) throws TaskNotFound;
	Task updateTask(TaskUpdateRequest task, String taskId) throws TaskNotFound;
	Optional<Task> getTask(String taskId) throws TaskNotFound;
	List<Task> getAllTasks();
	List<Task> getSubTasks(String taskId) throws TaskNotFound;
	Task addSubTask(String taskId, Task subTask) throws TaskNotFound;
	ResponseEntity deleteSubTask(String taskId, String subTaskId) throws TaskNotFound;
	//Task deleteMultipleSubTask(String taskId, List<String> subTaskIds) throws TaskNotFound;
	Task updateSubTask(String taskId, TaskUpdateRequest subTaskRequest, String subTaskId) throws TaskNotFound;
	Task getSubTask(String taskId, String subTaskId) throws TaskNotFound;
	String deleteMultipleTasks(List<String> taskIds);
	
}
