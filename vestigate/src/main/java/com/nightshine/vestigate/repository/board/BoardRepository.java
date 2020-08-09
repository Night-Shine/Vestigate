package com.nightshine.vestigate.repository.board;

import com.nightshine.vestigate.model.board.Board;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface BoardRepository extends JpaRepository<Board,UUID> {

    @Query("SELECT P FROM Board P WHERE P.isDeleted=false and P.id IN :boardIds")
    List<Board> getBoardsByIds(List<UUID> boardIds);

    @Query("SELECT P FROM Board P WHERE P.isDeleted=false and P.id=:boardId")
    Optional<Board> findById(UUID boardId);



    @Modifying
    @Query("UPDATE Board c SET c.isDeleted=true WHERE c.id IN :ids")
    void deleteAll(List<UUID> ids);

    @Modifying
    @Query("UPDATE Board c SET c.isDeleted=true WHERE c.id=:id")
    void deleteById(UUID id);

}
