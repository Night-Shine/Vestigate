package com.nightshine.vestigate.repository.task;

import java.util.List;
import java.util.Optional;

import com.nightshine.vestigate.model.Task;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface TaskRepository extends MongoRepository<Task,String>, CustomTaskRepository<Task, String> {
    @Query("{'id':?0,isDeleted:false}")
    Task findByTaskId(String taskId);
    @Query("{'isSubTask':false, 'isDeleted':false}")
    public List<Task> findAllTasks();
    @Query("{'id':?0,isDeleted:false}")
    Optional<Task> findByTaskIdOptional(String taskId);
    boolean existsById(String taskId);
}
 