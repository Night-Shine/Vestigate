package com.nightshine.vestigate.service;

import com.mongodb.client.result.UpdateResult;
import com.nightshine.vestigate.exception.BoardNotFound;
import com.nightshine.vestigate.exception.ProjectNotFound;
import com.nightshine.vestigate.model.Board;
import com.nightshine.vestigate.payload.request.BoardsUpdateRequest;
import com.nightshine.vestigate.repository.boards.BoardsRepository;
import com.nightshine.vestigate.utils.Helper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BoardsService {
    @Autowired
    private ProjectService projectService;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private BoardsRepository boardsRepository;



    public void addBoards(String projectId, Board board) throws Exception {
        Board savedBoard = boardsRepository.save(board);
        String boardId = savedBoard.getId();
        boolean isAdded = projectService.addBoardToProject(projectId,boardId);
       if(!isAdded){
            throw new ProjectNotFound("Project Does Not Exits");
        }
    }

    public void deleteBoard(String projectId,String boardId) throws Exception {
        Query query1 = new Query();
        query1.addCriteria(Criteria.where("id").is(boardId));
        Board board = mongoTemplate.findOne(query1, Board.class);
        if(board != null){
            Update update = new Update();
            update.set("isDeleted", true);
            UpdateResult p = mongoTemplate.updateFirst(query1, update, Board.class);
            boolean isDeleted = projectService.deleteBoardFromProject(projectId,board.getId());
            if(!isDeleted)
                throw new ProjectNotFound("Project Does Not Exits");

        }
        else{
            throw new BoardNotFound("Board Does Not Exits");
        }
    }

    public List<Board> getBoardsByIds(List<String> boardsIds) throws Exception {
        List<Board> boards = boardsRepository.getBoardsByIds(boardsIds);
        if(boards.size() == 0){
            throw new BoardNotFound("No such boards");
        }
        else{
            return boards;
        }
    }

    public void addTaskToBacklogs(String boardId,String taskId) throws BoardNotFound {
        Board board = boardsRepository.getBoardById(boardId);
        if(board == null)
            throw new BoardNotFound("Board not found");
        List<String> backlogs = board.getBacklogs();

        if(backlogs == null){
            List<String> back = new ArrayList<String>();
            back.add(taskId);
            board.setBacklogs(back);
        }
        else{
            backlogs.add(taskId);
            board.setBacklogs(backlogs);
        }
        boardsRepository.save(board);

    }

    public String deleteMultipleBoards(List<String > boardIds){
        boardsRepository.deleteAll(boardIds);
        return "Deleted Multiple the Board";
    }

    public Board updateBoard(BoardsUpdateRequest boardUpdateRequest, String  boardId) throws BoardNotFound {
        Board board =  boardsRepository.getBoardById(boardId);
        if(board == null)
            throw new BoardNotFound("No such Board found");
        Helper.copyBoardDetails(board,boardUpdateRequest);
        return boardsRepository.save(board);
    }
}
