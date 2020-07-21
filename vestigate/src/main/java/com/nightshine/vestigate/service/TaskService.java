package com.nightshine.vestigate.service;

import com.nightshine.vestigate.exception.TaskNotFound;
import com.nightshine.vestigate.model.Task;
import com.nightshine.vestigate.payload.request.TaskUpdateRequest;

import java.util.List;



public interface TaskService {
	
	Task addTask(Task task);
	Task deleteTask(String taskId);
	Task updateTask(TaskUpdateRequest task, String taskId) throws TaskNotFound;
	Task getTask(String taskId);
	List<Task> getAllTasks();
	List<Task> getSubTasks(String taskId);
	Task addSubTask(String taskId, Task subTask) throws TaskNotFound;
	Task deleteSubTask(String taskId, String subTaskId) throws TaskNotFound;
	//Task deleteMultipleSubTask(String taskId, List<String> subTaskIds) throws TaskNotFound;
	Task updateSubTask(String taskId, TaskUpdateRequest subTaskRequest, String subTaskId) throws TaskNotFound;
	Task getSubTask(String taskId, String subTaskId) throws TaskNotFound;
	String deleteMultipleTasks(List<String> taskIds);
	
}
