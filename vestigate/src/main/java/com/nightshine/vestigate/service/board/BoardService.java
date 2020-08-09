package com.nightshine.vestigate.service.board;

import com.nightshine.vestigate.exception.board.BoardNotFound;
import com.nightshine.vestigate.exception.project.ProjectNotFound;
import com.nightshine.vestigate.model.board.Board;
import com.nightshine.vestigate.payload.request.board.BoardUpdateRequest;
import com.nightshine.vestigate.repository.board.BoardRepository;
import com.nightshine.vestigate.service.project.ProjectService;
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
public class BoardService {
    @Autowired
    private ProjectService projectService;



    @Autowired
    private BoardRepository boardRepository;



    public void addBoards(UUID projectId, Board board) throws Exception {
        Board savedBoard = boardRepository.save(board);
        UUID boardId = savedBoard.getId();
        boolean isAdded = projectService.addBoardToProject(projectId,boardId);
       if(!isAdded){
            throw new ProjectNotFound("Project Does Not Exits");
        }
    }

    public void deleteBoard(UUID projectId,UUID boardId) throws Exception {

        Optional<Board> board = boardRepository.findById(boardId);
        if(board.isPresent()){
            boardRepository.deleteById(boardId);
            boolean isDeleted = projectService.deleteBoardFromProject(projectId,boardId);
            if(!isDeleted)
                throw new ProjectNotFound("Project Does Not Exits");
        }
        else{
            throw new BoardNotFound("Board Does Not Exits");
        }
    }

    public List<Board> getBoardsByIds(List<UUID> boardsIds) throws Exception {
        List<Board> boards = boardRepository.getBoardsByIds(boardsIds);
        if(boards.size() == 0){
            throw new BoardNotFound("No such boards");
        }
        else{
            return boards;
        }
    }

    public void addTaskToBacklogs(UUID boardId,UUID taskId) throws BoardNotFound {
        Optional<Board> board = boardRepository.findById(boardId);
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
        boardRepository.save(b);

    }

    public String deleteMultipleBoards(UUID projectId,List<UUID > boardIds) throws Exception {
        boardRepository.deleteAll(boardIds);
        for(UUID id:boardIds){
            projectService.deleteBoardFromProject(projectId,id);
        }
        return "Deleted Multiple the Board";
    }

    public Board updateBoard(BoardUpdateRequest boardUpdateRequest, UUID  boardId) throws BoardNotFound {
        Optional<Board> board =  boardRepository.findById(boardId);

        if(!board.isPresent())
            throw new BoardNotFound("No such Board found");
        Board b = board.get();
        Helper.copyBoardDetails(b,boardUpdateRequest);
        return boardRepository.save(b);
    }
}
