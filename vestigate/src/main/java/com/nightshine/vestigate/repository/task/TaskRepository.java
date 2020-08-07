package com.nightshine.vestigate.repository.task;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.nightshine.vestigate.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepository extends
        JpaRepository<Task, UUID> {

    @Query("SELECT T FROM Task T WHERE T.isDeleted=false and T.isSubTask = false")
    List<Task> findAllTasks();

    @Query("SELECT T FROM Task T WHERE T.isDeleted=false and T.id=:id")
    Optional<Task> findByIdOptional(UUID id);

    @Query("SELECT T FROM Task T WHERE T.isDeleted=false and T.id=:id")
    Task findByTaskId(UUID id);

    @Modifying
    @Query("UPDATE Task t SET t.isDeleted=true WHERE t.id=:id")
    void deleteById(UUID id);
}
 