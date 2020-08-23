package com.nightshine.vestigate.service.board;

import com.nightshine.vestigate.exception.board.BoardNotFound;
import com.nightshine.vestigate.exception.project.ProjectNotFound;
import com.nightshine.vestigate.model.board.Board;
import com.nightshine.vestigate.model.project.Project;
import com.nightshine.vestigate.model.team.Team;
import com.nightshine.vestigate.payload.request.board.BoardUpdateRequest;
import com.nightshine.vestigate.payload.response.ApiResponse;
import com.nightshine.vestigate.repository.board.BoardRepository;
import com.nightshine.vestigate.repository.project.ProjectRepository;
import com.nightshine.vestigate.service.project.ProjectService;
import com.nightshine.vestigate.utils.Helper;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;

@Transactional
@Service
public class BoardService {
    @Autowired
    private ProjectService projectService;

    @Autowired
    private ProjectRepository projectRepo;

    @Autowired
    private BoardRepository boardRepository;



    public ResponseEntity<?> addBoards(UUID projectId, Board board) throws Exception {
        Optional<Project> project = projectRepo.findById(projectId);
        if(!project.isPresent()){
            return new ResponseEntity(new ApiResponse(false, "Project does not exists!"),
                    HttpStatus.BAD_REQUEST);
        }
        else {
            Board savedBoard = boardRepository.save(board);
            projectService.addBoardToProject(projectId, savedBoard.getId()).getBody();
            return new ResponseEntity(savedBoard, HttpStatus.CREATED);
        }
    }

    public ResponseEntity<?> deleteBoard(UUID projectId,UUID boardId) throws Exception {

        Optional<Board> board = boardRepository.findById(boardId);
        if(board.isPresent()){
            boardRepository.deleteById(boardId);
            String isDeleted =  projectService.deleteBoardFromProject(projectId,boardId).getBody().toString();
            if(isDeleted.equals("false"))
                return new ResponseEntity(new ApiResponse(false, "Project does not exists!"),
                        HttpStatus.BAD_REQUEST);
            else{
                return new ResponseEntity(board, HttpStatus.OK);
            }
        }
        else{
            return new ResponseEntity(new ApiResponse(false, "Board does not exists!"),
                    HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<?> getBoardsByIds(List<UUID> boardsIds) throws Exception {
        List<Board> boards = boardRepository.getBoardsByIds(boardsIds);
        if(boards.size() == 0){
            return new ResponseEntity(new ApiResponse(false, "Board does not exists!"),
                    HttpStatus.BAD_REQUEST);
        }
        else{
            return new ResponseEntity(boards, HttpStatus.OK);
        }
    }

    public ResponseEntity<?> addTaskToBacklogs(UUID boardId,UUID taskId) throws BoardNotFound {
        Optional<Board> board = boardRepository.findById(boardId);
        if(!board.isPresent())
            return new ResponseEntity(new ApiResponse(false, "Board does not exists!"),
                    HttpStatus.BAD_REQUEST);
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
        return new ResponseEntity(boardRepository.save(b),HttpStatus.OK);

    }

    public ResponseEntity<?> deleteMultipleBoards(UUID projectId,List<UUID > boardIds) throws Exception {
        Optional<Project> project = projectRepo.findById(projectId);
        if(!project.isPresent())
            return new ResponseEntity(new ApiResponse(false, "Project does not exists!"),
                    HttpStatus.BAD_REQUEST);
        List<Boolean> isExists =new ArrayList<Boolean>(Arrays.asList(new Boolean[boardIds.size()]));
        Collections.fill(isExists, Boolean.TRUE);

        boardIds.forEach(bid -> {
            Optional<Board> board = boardRepository.findById(bid);
            if (!board.isPresent()) {
                int index = boardIds.indexOf(bid);
                isExists.set(index,false);
            }
        });

        Boolean f = false;
        if(isExists.indexOf(f) > 0) {
            String msg =  (isExists.indexOf(f)+1) + " Board does not exists! " ;
            return new ResponseEntity(new ApiResponse(false, msg),
                    HttpStatus.BAD_REQUEST);
        }
        boardRepository.deleteAll(boardIds);
        for(UUID id:boardIds){
            projectService.deleteBoardFromProject(projectId,id);
        }
        return new ResponseEntity(new ApiResponse(true, "Boards deleted successfully"),
                HttpStatus.OK);
    }

    public ResponseEntity<?> updateBoard(BoardUpdateRequest boardUpdateRequest, UUID  boardId) throws BoardNotFound {
        Optional<Board> board =  boardRepository.findById(boardId);

        if(!board.isPresent())
            return new ResponseEntity(new ApiResponse(false, "Board does not exists!"),
                    HttpStatus.BAD_REQUEST);
        Board b = board.get();
        Helper.copyBoardDetails(b,boardUpdateRequest);
        return new ResponseEntity(boardRepository.save(b),HttpStatus.OK);
    }
}
