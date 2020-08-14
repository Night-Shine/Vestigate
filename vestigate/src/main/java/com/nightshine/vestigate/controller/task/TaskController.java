package com.nightshine.vestigate.controller.task;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


import com.nightshine.vestigate.exception.task.TaskNotFound;
import com.nightshine.vestigate.model.task.SubTask;
import com.nightshine.vestigate.model.task.Task;
import com.nightshine.vestigate.payload.request.task.SubTaskUpdateRequest;
import com.nightshine.vestigate.payload.request.task.TaskUpdateRequest;
import com.nightshine.vestigate.service.task.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;

@RestController
@RequestMapping("/tasks")
public class TaskController {

	@Autowired
	TaskService taskService;

	@PostMapping("/addTask")
	public ResponseEntity<Task> addTask(@Valid @RequestBody Task task){
		return (taskService.addTask(task));
	}

	@DeleteMapping("/deleteTask")
	public ResponseEntity deleteTask(@RequestParam UUID taskId) throws TaskNotFound {
		return taskService.deleteTask(taskId);
	}

	@PutMapping("/updateTask/{taskId}")
	public ResponseEntity<Task> updateTask(@Valid @RequestBody TaskUpdateRequest taskUpdateRequest, @PathVariable UUID taskId) throws TaskNotFound {
		return taskService.updateTask(taskUpdateRequest, taskId);
	}

	@GetMapping("/taskId")
	public ResponseEntity<Optional<Task>> getTask(@RequestParam UUID taskId) throws TaskNotFound {
		return taskService.getTask(taskId);
	}

	@GetMapping("/getAllTasks")
	public ResponseEntity<List<Task>> getAllTasks() {
		return taskService.getAllTasks();
	}

	@PostMapping("/{taskId}/subTasks/add")
	public ResponseEntity<Task> addSubTask(@PathVariable UUID taskId, @Valid @RequestBody SubTask subTask) throws TaskNotFound {
		return taskService.addSubTask(taskId, subTask);
	}

	@DeleteMapping("/{taskId}/subTasks/delete/{subTaskId}")
	public ResponseEntity deleteSubTask(@PathVariable UUID taskId, @PathVariable UUID subTaskId) throws TaskNotFound{
		return taskService.deleteSubTask(taskId,subTaskId);
	}

	@PutMapping("/{taskId}/subTasks/update/{subTaskId}")
	public ResponseEntity<SubTask> updateSubTask(@PathVariable UUID taskId, @Valid @RequestBody SubTaskUpdateRequest subTaskRequest, @PathVariable UUID subTaskId) throws TaskNotFound{
		return taskService.updateSubTask(taskId, subTaskRequest, subTaskId);
	}

	@GetMapping("/{taskId}/subTasks/{subTaskId}")
	public ResponseEntity<Optional<SubTask>> getSubTask(@PathVariable UUID taskId, @PathVariable UUID subTaskId) throws TaskNotFound{
		return taskService.getSubTask(taskId, subTaskId);
	}

	@GetMapping("/{taskId}/subTasks")
	public ResponseEntity<List<SubTask>> getSubTasks(@PathVariable UUID taskId) throws TaskNotFound {
		return taskService.getSubTasks(taskId);
	}

	@DeleteMapping("/deleteMultipleTasks")
	public ResponseEntity deleteMultipleTasks(@Valid @RequestBody List<UUID> ids) throws TaskNotFound {
		return taskService.deleteMultipleTasks(ids);
	}
}
