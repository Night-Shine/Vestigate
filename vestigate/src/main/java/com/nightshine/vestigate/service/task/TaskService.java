package com.nightshine.vestigate.service.task;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

import com.nightshine.vestigate.exception.generic.BadRequestException;
import com.nightshine.vestigate.exception.task.TaskNotFound;
import com.nightshine.vestigate.model.task.SubTask;
import com.nightshine.vestigate.model.task.Task;
import com.nightshine.vestigate.payload.request.task.SubTaskUpdateRequest;
import com.nightshine.vestigate.payload.request.task.TaskUpdateRequest;
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

	public ResponseEntity<Task> addTask(Task task) {
		try {
			if (task.getTitle() != null || task.getReporter() != null) {
				return new ResponseEntity<>(repo.save(task), HttpStatus.CREATED);
			} else throw new BadRequestException("Not a valid request");
		}catch(BadRequestException badRequestException){
			return new ResponseEntity(HttpStatus.BAD_REQUEST);
		}
	}

	public ResponseEntity deleteTask(UUID taskId) throws TaskNotFound {
		try {
			Task deletedTask = repo.findByTaskId(taskId);
			if (deletedTask != null) {
				List<SubTask> subTasks = deletedTask.getSubTask();
				List<UUID> subTasksIds = new ArrayList<>();
				if (subTasks.size() > 0) {
					for (SubTask st : subTasks) {
						subTasksIds.add(st.getId());
					}
				}
				subTaskService.deleteMultipleSubTasks(subTasksIds);
				repo.deleteById(taskId);
				return new ResponseEntity(HttpStatus.OK);
			} else throw new TaskNotFound("Task is Null");
		}catch (TaskNotFound taskNotFound){
			return new ResponseEntity(HttpStatus.NOT_FOUND);
		}catch(Exception exception){
			return new ResponseEntity(HttpStatus.BAD_REQUEST);
		}
	}

	public ResponseEntity<Task> updateTask(TaskUpdateRequest taskUpdateRequest, UUID taskId) throws TaskNotFound {
		try {
			Optional<Task> optionalTask = repo.findByIdOptional(taskId);
			if (!optionalTask.isPresent() || !repo.existsById(taskId)) {
				throw new TaskNotFound("Task doesn't exists!");
			}
			Task task = optionalTask.get();
			Helper.copyTaskDetails(task, taskUpdateRequest);
			return new ResponseEntity<>(repo.save(task),HttpStatus.ACCEPTED);
		}catch(TaskNotFound taskNotFound){
			return new ResponseEntity(HttpStatus.NOT_ACCEPTABLE);
		}
	}

	public ResponseEntity<Optional<Task>> getTask(UUID taskId) throws TaskNotFound {
		try {
			Optional<Task> task = repo.findByIdOptional(taskId);
			if (task.isPresent()) {
				return new ResponseEntity<>(task, HttpStatus.FOUND);
			} else throw new TaskNotFound("No task is available");
		}catch (TaskNotFound taskNotFound){
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}catch(Exception exception){
			return new ResponseEntity(HttpStatus.BAD_REQUEST);
		}
	}

	public ResponseEntity<List<Task>> getAllTasks() {
			return new ResponseEntity(repo.findAllTasks(),HttpStatus.FOUND);
	}

	public ResponseEntity<Task> addSubTask(UUID taskId, SubTask subTask) throws TaskNotFound {
		try {
			Task task = repo.findByTaskId(taskId);
			if (task != null) {
				try {
					SubTask st = subTaskService.addSubTask(subTask);
					if (subTask.getTitle() != null || subTask.getReporter() != null) {
						task.getSubTask().add(st);
						repo.save(task);
					} else throw new BadRequestException("Not a valid request");
				}catch(BadRequestException badRequestException){
					return new ResponseEntity(HttpStatus.BAD_REQUEST);
				}
				return new ResponseEntity<>(task,HttpStatus.ACCEPTED);
			} else {
				throw new TaskNotFound("No Task Available to Update Sub task");
			}
		}catch (TaskNotFound taskNotFound){
			return new ResponseEntity(HttpStatus.NOT_ACCEPTABLE);
		}
	}

	public ResponseEntity deleteSubTask(UUID taskId, UUID subTaskId) throws TaskNotFound {
		try {
			Task task = repo.findByTaskId(taskId);
			if (task != null) {
				ResponseEntity subTaskStatus = new ResponseEntity(HttpStatus.BAD_REQUEST);
				List<SubTask> subTasks = task.getSubTask();
				for (SubTask st : subTasks) {
					if (st.getId().equals(subTaskId)) {
						subTaskStatus = subTaskService.deleteSubTask(subTaskId);
						subTasks.remove(st);
						break;
					}
				}
				task.setSubTask(subTasks);
				repo.save(task);
				return subTaskStatus;
			} else throw new TaskNotFound("No Task Available to delete Subtask");
		}catch(TaskNotFound taskNotFound){
			return new ResponseEntity(HttpStatus.NOT_FOUND);
		}catch(Exception exception){
			return new ResponseEntity(HttpStatus.NOT_ACCEPTABLE);
		}
	}

	public ResponseEntity<SubTask> updateSubTask(UUID taskId, SubTaskUpdateRequest subTaskRequest, UUID subTaskId) throws TaskNotFound {
		try {
			ResponseEntity<SubTask> subTask = new ResponseEntity(HttpStatus.NOT_FOUND);
			Task task = repo.findByTaskId(taskId);
			if (task != null) {
				List<SubTask> subTasks = task.getSubTask();
				for (SubTask st : subTasks) {
					if (st.getId().equals(subTaskId)) {
						subTask = subTaskService.updateSubTask(subTaskRequest, subTaskId);
					}
				}
				task.setSubTask(subTasks);
				repo.save(task);
				return subTask;
			} else throw new TaskNotFound("No Task Available!!");
		}catch(TaskNotFound taskNotFound){
			return new ResponseEntity(HttpStatus.NOT_FOUND);
		}catch(Exception exception){
			return new ResponseEntity(HttpStatus.BAD_REQUEST);
		}
	}

	public ResponseEntity<Optional<SubTask>> getSubTask(UUID taskId, UUID subTaskId) throws TaskNotFound {
		try {
			ResponseEntity<Optional<SubTask>> subTask = null;
			Task task = repo.findByTaskId(taskId);
			if (task != null) {
				subTask = subTaskService.getSubTask(subTaskId);
			} else {
				throw new TaskNotFound("No Task Available!!");
			}
			return subTask;
		}catch(TaskNotFound taskNotFound){
			return new ResponseEntity(HttpStatus.NOT_FOUND);
		}catch(Exception exception){
			return new ResponseEntity(HttpStatus.BAD_REQUEST);
		}

	}

	public ResponseEntity<List<SubTask>> getSubTasks(UUID taskId) throws TaskNotFound {
		try {
			Task task = repo.findByTaskId(taskId);
			ResponseEntity<List<SubTask>> subTaskList;
			if (task != null) {
				subTaskList = new ResponseEntity(task.getSubTask(), HttpStatus.FOUND);
				return subTaskList;
			} else throw new TaskNotFound("Invalid Task Id");
		}catch(TaskNotFound taskNotFound){
			return new ResponseEntity(HttpStatus.NOT_FOUND);
		}catch(Exception exception){
			return new ResponseEntity(HttpStatus.BAD_REQUEST);
		}
	}

	public ResponseEntity deleteMultipleTasks(List<UUID> taskIds){
		try {
			taskIds.forEach(taskId -> {
					ResponseEntity responseEntity = deleteTask(taskId);
					if(responseEntity.getStatusCode() != HttpStatus.OK){
						throw new TaskNotFound("Invaild Task id");
					}
			});
			return new ResponseEntity(HttpStatus.OK);
		}catch(TaskNotFound taskNotFound){
			return new ResponseEntity(HttpStatus.BAD_REQUEST);
		}catch(Exception exception){
			return new ResponseEntity(HttpStatus.NOT_ACCEPTABLE);
		}
	}
}
