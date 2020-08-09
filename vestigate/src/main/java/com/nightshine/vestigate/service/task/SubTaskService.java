package com.nightshine.vestigate.service.task;

import com.nightshine.vestigate.exception.task.TaskNotFound;
import com.nightshine.vestigate.model.task.SubTask;
import com.nightshine.vestigate.payload.request.task.SubTaskUpdateRequest;
import com.nightshine.vestigate.repository.task.SubTaskRepository;
import com.nightshine.vestigate.utils.Helper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
public class SubTaskService {

	@Autowired
	SubTaskRepository repo;

	public SubTask addSubTask(SubTask subTask) {
			return repo.save(subTask);
	}

	public ResponseEntity deleteSubTask(UUID subTaskId) throws TaskNotFound {
			SubTask deletedTask = repo.findBySubTaskId(subTaskId);
		if(deletedTask != null) {
			repo.deleteById(subTaskId);
			return new ResponseEntity(HttpStatus.OK);
		}
		else throw new TaskNotFound("No subTask available to delete");
	}

	public Optional<SubTask> getSubTask(UUID subTaskId) throws TaskNotFound {
			Optional<SubTask> subTask = repo.findByIdOptional(subTaskId);
		if(subTask != null){
			return subTask;
		}
		else throw new TaskNotFound("No subTask is available");
	}

	public List<SubTask> getAllSubTasks() {
			return repo.findAllSubTasks();
	}


	public SubTask updateSubTask(SubTaskUpdateRequest subTaskUpdateRequest, UUID subTaskId) throws TaskNotFound {
		Optional<SubTask> optionalSubTask = repo.findByIdOptional(subTaskId);
		if (!optionalSubTask.isPresent() || !repo.existsById(subTaskId)) {
			throw new TaskNotFound("SubTask doesn't exists!");
		}
		SubTask subTask = optionalSubTask.get();
		Helper.copySubTaskDetails(subTask, subTaskUpdateRequest);
		return repo.save(subTask);
	}

	public ResponseEntity deleteMultipleSubTasks(List<UUID> subTaskIds){
		subTaskIds.forEach(subTaskId -> {
			try {
				deleteSubTask(subTaskId);
			} catch (TaskNotFound taskNotFound) {
				taskNotFound.printStackTrace();
			}
		});
		return new ResponseEntity(HttpStatus.OK);
	}
}
