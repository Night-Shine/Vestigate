package com.nightshine.vestigate.repository.boards;

import com.nightshine.vestigate.model.Board;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BoardsRepository extends MongoRepository<Board,String>, CustomBoardsRepository<Board, String> {

    @Query("{'id':{$in:?0}}")
    List<Board> getBoardsByIds(List<String> boardIds);

    @Query("{'id':?0}")
    Board getBoardById(String boardId);

}
