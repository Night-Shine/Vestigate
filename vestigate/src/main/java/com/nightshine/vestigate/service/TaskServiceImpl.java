package com.nightshine.vestigate.service;

import java.util.List;
import java.util.Optional;

import com.nightshine.vestigate.exception.TaskNotFound;
import com.nightshine.vestigate.model.Task;
import com.nightshine.vestigate.payload.request.TaskUpdateRequest;
import com.nightshine.vestigate.repository.task.TaskRepository;
import com.nightshine.vestigate.utils.Helper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


@Service
public class TaskServiceImpl implements TaskService{
	
	@Autowired
	TaskRepository repo;
	
	@Override
	public Task addTask(Task task) {
		// TODO Auto-generated method stub
		return repo.save(task);
	}

	@Override
	public ResponseEntity deleteTask(String taskId) throws TaskNotFound {
		// TODO Auto-generated method stub
		Task deletedTask = repo.findByTaskId(taskId);
		if(deletedTask != null) {
			deletedTask.setIsDeleted(true);
			List<Task> subTasks = deletedTask.getSubTask();
			for(Task st : subTasks){
				st.setIsDeleted(true);
				deletedTask.getSubTask().remove(st);
			}
			repo.save(deletedTask);
			return new ResponseEntity(HttpStatus.OK);
		}
		else throw new TaskNotFound("No task available to delete");
		
	}

	@Override
	public Optional<Task> getTask(String taskId) throws TaskNotFound {
		// TODO Auto-generated method stub
		Optional<Task> task = repo.findByTaskIdOptional(taskId);
		if(task != null){
			return task;
		}
		else throw new TaskNotFound("No task is available");
	}

	@Override
	public List<Task> getAllTasks() {
		// TODO Auto-generated method stub
		return repo.findAllTasks();
	}

	@Override
	public List<Task> getSubTasks(String taskId) throws TaskNotFound {
		// TODO Auto-generated method stub
		Task task = repo.findByTaskId(taskId);
		if(task != null) {
			return task.getSubTask();
		}
		else throw new TaskNotFound("Invalid Task Id");
	}

	@Override
	public Task addSubTask(String taskId, Task subTask) throws TaskNotFound {
		// TODO Auto-generated method stub
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


	@Override
	public ResponseEntity deleteSubTask(String taskId, String subTaskId) throws TaskNotFound {
		Task task = repo.findByTaskId(taskId);
		if(task != null) {
			List<Task> subTasks = task.getSubTask();
			for(Task st : subTasks){
   				if(st.getId().equals(subTaskId)) {
   					st.setIsDeleted(true);
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

	@Override
	public Task updateTask(TaskUpdateRequest taskUpdateRequest, String taskId) throws TaskNotFound {
		Optional<Task> optionalTask = repo.findByTaskIdOptional(taskId);
		if(!optionalTask.isPresent() || !repo.existsById(taskId)) {
			throw new TaskNotFound( "Task doesn't exists!");
		}
		Task task = optionalTask.get();
		Helper.copyTaskDetails(task, taskUpdateRequest);
		return repo.save(task);
	}
	@Override
	public Task getSubTask(String taskId, String subTaskId) throws TaskNotFound {
		// TODO Auto-generated method stub
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

	@Override
	public Task updateSubTask(String taskId, TaskUpdateRequest subTaskRequest, String subTaskId) throws TaskNotFound {
		// TODO Auto-generated method stub
		boolean visited = false;
		Task subTask = null;
		Task task = repo.findByTaskId(taskId);
		if(task != null) {
			List<Task> subTasks = task.getSubTask();
			for(Task st : subTasks){
   				if(st.getId().equals(subTaskId)) {
   					st = TaskServiceImpl.this.updateTask(subTaskRequest, subTaskId);
   					subTask = st;
   					visited = true;
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
	
	@Override
	public String deleteMultipleTasks(List<String> taskIds){
        repo.deleteAll(taskIds);
        return "Deleted selected!";
    }

	
}
