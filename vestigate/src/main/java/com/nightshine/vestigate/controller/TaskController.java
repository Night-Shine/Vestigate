package com.nightshine.vestigate.controller;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


import com.nightshine.vestigate.exception.TaskNotFound;
import com.nightshine.vestigate.model.Task;
import com.nightshine.vestigate.payload.request.TaskUpdateRequest;
import com.nightshine.vestigate.service.TaskServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@RestController
@RequestMapping("/tasks")
public class TaskController {

	@Autowired
	TaskServiceImpl service;

	@PostMapping("/addTask")
	public ResponseEntity<Task> addTask(@Valid @RequestBody Task task){
		return new ResponseEntity<Task>(service.addTask(task),HttpStatus.CREATED);
	}

	@PutMapping("/updateTask/{taskId}")
	public ResponseEntity<Task> updateTask(@Valid @RequestBody TaskUpdateRequest taskUpdateRequest, @PathVariable UUID taskId) throws TaskNotFound {
		return new ResponseEntity<Task>(service.updateTask(taskUpdateRequest, taskId), HttpStatus.ACCEPTED);
	}

	@DeleteMapping("/deleteTask")
	public ResponseEntity deleteTask(@RequestParam UUID taskId) throws TaskNotFound {
		return service.deleteTask(taskId);
	}
	@GetMapping("/taskId")
	public ResponseEntity<Optional<Task>> getTask(@RequestParam UUID taskId) throws TaskNotFound {
		return new ResponseEntity<Optional<Task>>(service.getTask(taskId), HttpStatus.FOUND);
	}
	@GetMapping("/getAllTasks")
	public ResponseEntity<List<Task>> getAllTasks() {
		return new ResponseEntity<List<Task>>(service.getAllTasks(), HttpStatus.FOUND);
	}
	@GetMapping("/{taskId}/subTasks")
	public ResponseEntity<List<Task>> getSubTasks(@PathVariable UUID taskId) throws TaskNotFound {
		return new ResponseEntity<List<Task>>(service.getSubTasks(taskId), HttpStatus.FOUND);
	}

	@PostMapping("/{taskId}/subTasks/add")
	public ResponseEntity<Task> addSubTask(@PathVariable UUID taskId, @Valid @RequestBody Task subTask) throws TaskNotFound{
		return new ResponseEntity<Task>(service.addSubTask(taskId,subTask),HttpStatus.CREATED);
	}

	@PutMapping("/{taskId}/subTasks/update/{subTaskId}")
	public ResponseEntity<Task> updateSubTask(@PathVariable UUID taskId, @Valid @RequestBody TaskUpdateRequest subTaskRequest, @PathVariable UUID subTaskId) throws TaskNotFound{
		return new ResponseEntity<Task>(service.updateSubTask(taskId,subTaskRequest,subTaskId), HttpStatus.ACCEPTED);
	}

	@DeleteMapping("/{taskId}/subTasks/delete/{subTaskId}")
	public ResponseEntity deleteSubTask(@PathVariable UUID taskId, @PathVariable UUID subTaskId) throws TaskNotFound{
		return service.deleteSubTask(taskId,subTaskId);
	}
	@GetMapping("/{taskId}/subTasks/{subTaskId}")
	public ResponseEntity<Task> getSubTask(@PathVariable UUID taskId, @PathVariable UUID subTaskId) throws TaskNotFound{
		return new ResponseEntity<Task>(service.getSubTask(taskId, subTaskId), HttpStatus.FOUND);
	}

	@DeleteMapping("/deleteMultipleTasks")
	public ResponseEntity deleteMultipleTasks(@Valid @RequestBody List<UUID> ids) throws TaskNotFound {
		return service.deleteMultipleTasks(ids);
	}
}
