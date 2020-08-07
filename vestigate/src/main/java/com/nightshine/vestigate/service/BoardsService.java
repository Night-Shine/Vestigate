package com.nightshine.vestigate.service;

import com.nightshine.vestigate.exception.BoardNotFound;
import com.nightshine.vestigate.exception.ProjectNotFound;
import com.nightshine.vestigate.model.Board;
import com.nightshine.vestigate.payload.request.BoardsUpdateRequest;
import com.nightshine.vestigate.repository.boards.BoardsRepository;
import com.nightshine.vestigate.utils.Helper;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Transactional
@Service
public class BoardsService {
    @Autowired
    private ProjectService projectService;



    @Autowired
    private BoardsRepository boardsRepository;



    public void addBoards(UUID projectId, Board board) throws Exception {
        Board savedBoard = boardsRepository.save(board);
        UUID boardId = savedBoard.getId();
        boolean isAdded = projectService.addBoardToProject(projectId,boardId);
       if(!isAdded){
            throw new ProjectNotFound("Project Does Not Exits");
        }
    }

    public void deleteBoard(UUID projectId,UUID boardId) throws Exception {

        Optional<Board> board = boardsRepository.findById(boardId);
        if(board.isPresent()){
            boardsRepository.deleteById(boardId);
            boolean isDeleted = projectService.deleteBoardFromProject(projectId,boardId);
            if(!isDeleted)
                throw new ProjectNotFound("Project Does Not Exits");
        }
        else{
            throw new BoardNotFound("Board Does Not Exits");
        }
    }

    public List<Board> getBoardsByIds(List<UUID> boardsIds) throws Exception {
        List<Board> boards = boardsRepository.getBoardsByIds(boardsIds);
        if(boards.size() == 0){
            throw new BoardNotFound("No such boards");
        }
        else{
            return boards;
        }
    }

    public void addTaskToBacklogs(UUID boardId,UUID taskId) throws BoardNotFound {
        Optional<Board> board = boardsRepository.findById(boardId);
        if(!board.isPresent())
            throw new BoardNotFound("Board not found");
        Board b = board.get();
        List<UUID> backlogs = b.getBacklogs();

        if(backlogs == null){
            List<UUID> back = new ArrayList<UUID>();
            back.add(taskId);
            b.setBacklogs(back);
        }
        else{
            backlogs.add(taskId);
            b.setBacklogs(backlogs);
        }
        boardsRepository.save(b);

    }

    public String deleteMultipleBoards(UUID projectId,List<UUID > boardIds) throws Exception {
        boardsRepository.deleteAll(boardIds);
        for(UUID id:boardIds){
            projectService.deleteBoardFromProject(projectId,id);
        }
        return "Deleted Multiple the Board";
    }

    public Board updateBoard(BoardsUpdateRequest boardUpdateRequest, UUID  boardId) throws BoardNotFound {
        Optional<Board> board =  boardsRepository.findById(boardId);

        if(!board.isPresent())
            throw new BoardNotFound("No such Board found");
        Board b = board.get();
        Helper.copyBoardDetails(b,boardUpdateRequest);
        return boardsRepository.save(b);
    }
}
