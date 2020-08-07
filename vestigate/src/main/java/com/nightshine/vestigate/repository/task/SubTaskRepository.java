package com.nightshine.vestigate.repository.task;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import com.nightshine.vestigate.model.SubTask;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface SubTaskRepository extends
        JpaRepository<SubTask, UUID> {

    @Query("SELECT T FROM SubTask T WHERE T.isDeleted=false and T.isSubTask = true")
    List<SubTask> findAllSubTasks();

    @Query("SELECT T FROM SubTask T WHERE T.isDeleted=false and T.id=:id")
    Optional<SubTask> findByIdOptional(UUID id);

    @Query("SELECT T FROM SubTask T WHERE T.isDeleted=false and T.id=:id")
    SubTask findBySubTaskId(UUID id);

    @Modifying
    @Query("UPDATE SubTask t SET t.isDeleted=true WHERE t.id=:id")
    void deleteById(UUID id);
}
