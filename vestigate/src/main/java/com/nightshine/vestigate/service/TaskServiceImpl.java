package com.nightshine.vestigate.service;

import java.util.List;
import java.util.Optional;

import com.nightshine.vestigate.exception.TaskNotFound;
import com.nightshine.vestigate.model.Task;
import com.nightshine.vestigate.payload.request.TaskUpdateRequest;
import com.nightshine.vestigate.repository.task.TaskRepository;
import com.nightshine.vestigate.utils.Helper;
import org.springframework.beans.factory.annotation.Autowired;
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
	public Task deleteTask(String taskId) {
		// TODO Auto-generated method stub
		Task deletedTask = repo.findByTaskId(taskId);
		deletedTask.setIsDeleted(true);
		repo.save(deletedTask);
		return deletedTask;
		
	}

	@Override
	public Task getTask(String taskId) {
		// TODO Auto-generated method stub
		return repo.findByTaskId(taskId);
	}

	@Override
	public List<Task> getAllTasks() {
		// TODO Auto-generated method stub
		return repo.findAllTasks();
	}

	@Override
	public List<Task> getSubTasks(String taskId) {
		// TODO Auto-generated method stub
		Task task = repo.findByTaskId(taskId);
		return task.getSubTask();
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
	public Task deleteSubTask(String taskId, String subTaskId) throws TaskNotFound {
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
			return task;
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
   					return st;
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
   					subTask = TaskServiceImpl.this.updateTask(subTaskRequest, subTaskId);
   					visited = true;
   				}
			}
			if(!visited) throw new TaskNotFound("No subtask available");
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
