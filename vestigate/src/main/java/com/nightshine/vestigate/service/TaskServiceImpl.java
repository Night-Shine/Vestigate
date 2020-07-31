package com.nightshine.vestigate.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


import com.nightshine.vestigate.exception.TaskNotFound;
import com.nightshine.vestigate.model.Task;
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
public class TaskServiceImpl {

	@Autowired
	TaskRepository repo;

	public Task addTask(Task task) {
			return repo.save(task);
	}

	public ResponseEntity deleteTask(UUID taskId) throws TaskNotFound {
			Task deletedTask = repo.findByTaskId(taskId);
		if(deletedTask != null) {
			List<Task> subTasks = deletedTask.getSubTask();
			List<UUID> subTasksIds = new ArrayList<>();
			if(subTasks.size() > 0) {
				for (Task st : subTasks) {
					subTasksIds.add(st.getId());
				}
				repo.deleteAll(subTasksIds);
			}
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

	public List<Task> getSubTasks(UUID taskId) throws TaskNotFound {
			Task task = repo.findByTaskId(taskId);
		if(task != null) {
			return task.getSubTask();
		}
		else throw new TaskNotFound("Invalid Task Id");
	}

	public Task addSubTask(UUID taskId, Task subTask) throws TaskNotFound {
			Task task = repo.findByTaskId(taskId);
		if(task != null) {
			subTask.setIsSubTask(true);
			repo.save(subTask);
			task.getSubTask().add(subTask);
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
			List<Task> subTasks = task.getSubTask();
			for(Task st : subTasks){
				if(st.getId().equals(subTaskId)) {
					repo.deleteById(st.getId());
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

	public Task getSubTask(UUID taskId, UUID subTaskId) throws TaskNotFound {
			Task subTask = null;
		Task task = repo.findByTaskId(taskId);
		if(task != null) {
			List<Task> subTasks = task.getSubTask();
			for(Task st : subTasks){
				if(st.getId().equals(subTaskId)) {
					subTask = st;
				}
			}
		}
		else {
			throw new TaskNotFound("No Task Available!!");
		}
		return subTask;
	}

	public Task updateSubTask(UUID taskId, TaskUpdateRequest subTaskRequest, UUID subTaskId) throws TaskNotFound {
			boolean visited = false;
		Task subTask = null;
		Task task = repo.findByTaskId(taskId);
		if(task != null) {
			List<Task> subTasks = task.getSubTask();
			for(Task st : subTasks){
				if(st.getId().equals(subTaskId)) {
					subTask = st;
					visited = true;
					Helper.copyTaskDetails(subTask, subTaskRequest);
					repo.save(subTask);
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
