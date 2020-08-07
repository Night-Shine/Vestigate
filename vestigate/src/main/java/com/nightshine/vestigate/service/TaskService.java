package com.nightshine.vestigate.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.nightshine.vestigate.exception.TaskNotFound;
import com.nightshine.vestigate.model.SubTask;
import com.nightshine.vestigate.model.Task;
import com.nightshine.vestigate.payload.request.SubTaskUpdateRequest;
import com.nightshine.vestigate.payload.request.TaskUpdateRequest;
import com.nightshine.vestigate.repository.task.TaskRepository;
import com.nightshine.vestigate.utils.Helper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class TaskService {

	@Autowired
	TaskRepository repo;

	@Autowired
	SubTaskService subTaskService;

	public Task addTask(Task task) {
			return repo.save(task);
	}

	public ResponseEntity deleteTask(UUID taskId) throws TaskNotFound {
			Task deletedTask = repo.findByTaskId(taskId);
		if(deletedTask != null) {
			List<SubTask> subTasks = deletedTask.getSubTask();
			List<UUID> subTasksIds = new ArrayList<>();
			if(subTasks.size() > 0) {
				for (SubTask st : subTasks) {
					subTasksIds.add(st.getId());
				}
			}
			subTaskService.deleteMultipleSubTasks(subTasksIds);
			repo.deleteById(taskId);
			return new ResponseEntity(HttpStatus.OK);
		}
		else throw new TaskNotFound("No task available to delete");
	}

	public Optional<Task> getTask(UUID taskId) throws TaskNotFound {
		Optional<Task> task = repo.findByIdOptional(taskId);
		if(task != null){
			return task;
		}
		else throw new TaskNotFound("No task is available");
	}

	public List<Task> getAllTasks() {
			return repo.findAllTasks();
	}

	public List<SubTask> getSubTasks(UUID taskId) throws TaskNotFound {
		Task task = repo.findByTaskId(taskId);
		if(task != null) {
			return subTaskService.getAllSubTasks();
		}
		else throw new TaskNotFound("Invalid Task Id");
	}

	public Task addSubTask(UUID taskId, SubTask subTask) throws TaskNotFound {
		Task task = repo.findByTaskId(taskId);
		if(task != null) {
			SubTask st = subTaskService.addSubTask(subTask);
			task.getSubTask().add(st);
			repo.save(task);
			return task;
		}
		else {
			throw new TaskNotFound("No Task Available to Update Subtask");
		}
	}

	public ResponseEntity deleteSubTask(UUID taskId, UUID subTaskId) throws TaskNotFound {
		Task task = repo.findByTaskId(taskId);
		if(task != null) {
			List<SubTask> subTasks = task.getSubTask();
			for(SubTask st : subTasks){
				if(st.getId().equals(subTaskId)) {
					subTaskService.deleteSubTask(subTaskId);
					subTasks.remove(st);
					break;
				}
			}
			task.setSubTask(subTasks);
			repo.save(task);
			return new ResponseEntity(HttpStatus.OK);
		}
		else {
			throw new TaskNotFound("No Task Available to delete Subtask");
		}
	}

	public Task updateTask(TaskUpdateRequest taskUpdateRequest, UUID taskId) throws TaskNotFound {
		Optional<Task> optionalTask = repo.findByIdOptional(taskId);
		if(!optionalTask.isPresent() || !repo.existsById(taskId)) {
			throw new TaskNotFound( "Task doesn't exists!");
		}
		Task task = optionalTask.get();
		Helper.copyTaskDetails(task, taskUpdateRequest);
		return repo.save(task);
	}

	public Optional<SubTask> getSubTask(UUID taskId, UUID subTaskId) throws TaskNotFound {
		Optional<SubTask> subTask = null;
		Task task = repo.findByTaskId(taskId);
		if(task != null) {
			subTask = subTaskService.getSubTask(subTaskId);
		}
		else {
			throw new TaskNotFound("No Task Available!!");
		}
		return subTask;
	}

	public SubTask updateSubTask(UUID taskId, SubTaskUpdateRequest subTaskRequest, UUID subTaskId) throws TaskNotFound {
		boolean visited = false;
		SubTask subTask = null;
		Task task = repo.findByTaskId(taskId);
		if(task != null) {
			List<SubTask> subTasks = task.getSubTask();
			for(SubTask st : subTasks){
				if(st.getId().equals(subTaskId)) {
					visited = true;
					subTaskService.updateSubTask(subTaskRequest,subTaskId);
				}
			}
			task.setSubTask(subTasks);
			repo.save(task);
			if(!visited) throw new TaskNotFound("No subTask available");
		}
		else {
			throw new TaskNotFound("No Task Available!!");
		}
		return subTask;
	}

	public ResponseEntity deleteMultipleTasks(List<UUID> taskIds){
		taskIds.forEach(taskId -> {
			try {
				deleteTask(taskId);
			} catch (TaskNotFound taskNotFound) {
				taskNotFound.printStackTrace();
			}
		});
		return new ResponseEntity(HttpStatus.OK);
	}
}
