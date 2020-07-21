package com.nightshine.vestigate.controller;

import java.util.List;

import com.nightshine.vestigate.exception.TaskNotFound;
import com.nightshine.vestigate.model.Task;
import com.nightshine.vestigate.payload.request.TaskUpdateRequest;
import com.nightshine.vestigate.service.TaskServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;



@RestController
public class TaskController {
	
	@Autowired
	TaskServiceImpl service;
	
	@PostMapping("/tasks/add")
	public Task addTask(@RequestBody Task task){
		return service.addTask(task);
	}
	
	@PutMapping("/tasks/update/{taskId}")
	public Task updateTask(@RequestBody TaskUpdateRequest taskUpdateRequest, @PathVariable String taskId) throws TaskNotFound {
		return service.updateTask(taskUpdateRequest, taskId);
	}
	
	@DeleteMapping("/tasks/delete")
	public Task deleteTask(@RequestParam String taskId){
		return service.deleteTask(taskId);
	}
	
	@GetMapping("/tasks/id")
	public Task getTask(@RequestParam String taskId) {
		return service.getTask(taskId);
	}
	
	@GetMapping("/tasks/all")
	public List<Task> getAllTasks() {
		return service.getAllTasks();
	}
	
	@GetMapping("/subtasks/task-id")
	public List<Task> getSubTasks(@RequestParam String taskId) {
		return service.getSubTasks(taskId);
	}
	
	@PostMapping("/subtask/add")
	public Task addSubTask(@RequestParam String taskId, @RequestBody Task subTask) throws TaskNotFound{
		System.out.println(taskId + " " + subTask.getAssignee());
		return service.addSubTask(taskId,subTask);
	}
	
	@PutMapping("/subtask/update/{taskId}/{subTaskId}")
	public Task updateSubTask(@PathVariable String taskId, @RequestBody TaskUpdateRequest subTaskRequest, @PathVariable String subTaskId) throws TaskNotFound{
		return service.updateSubTask(taskId,subTaskRequest,subTaskId);
	}
	
	@DeleteMapping("/subtask/delete")
	public Task deleteSubTask(@RequestParam String taskId, @RequestParam String subTaskId) throws TaskNotFound{
		return service.deleteSubTask(taskId,subTaskId);
	}
	
	@GetMapping("/subtask/subtask-id")
	public Task getSubTask(@RequestParam String taskId, @RequestParam String subTaskId) throws TaskNotFound{
		return service.getSubTask(taskId, subTaskId);
	}
	
	@DeleteMapping("/deleteMultipleTasks")
    public ResponseEntity<?> deleteMultipleTasks(@RequestBody List<String> ids) {
        service.deleteMultipleTasks(ids);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
