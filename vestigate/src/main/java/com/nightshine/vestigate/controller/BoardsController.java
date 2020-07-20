package com.nightshine.vestigate.controller;

import com.nightshine.vestigate.exception.BoardNotFound;
import com.nightshine.vestigate.model.Board;
import com.nightshine.vestigate.payload.request.BoardsUpdateRequest;
import com.nightshine.vestigate.service.BoardsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/boards")
public class BoardsController {
    @Autowired
    private BoardsService boardsService;

    @PostMapping("/addBoard")
    private ResponseEntity<?> addBoard(@RequestBody Board board, @RequestParam String projectId) throws Throwable {
         boardsService.addBoards(projectId,board);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();

    }


    @DeleteMapping("/deleteBoard")
    private ResponseEntity<?> deleteTeam(@RequestParam String projectId,@RequestParam String boardId) throws Exception {
        boardsService.deleteBoard(projectId,boardId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

//    @PutMapping("/")
    @DeleteMapping("/deleteMultipleBoards")
    public ResponseEntity<?> deleteMultipleBoards(@Valid @RequestBody List<String> ids) {
        boardsService.deleteMultipleBoards(ids);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PutMapping("/updateAllBoards/{boardId}")
    public ResponseEntity<Board> updateUser(@Valid @RequestBody BoardsUpdateRequest boardUpdateRequest, @PathVariable String boardId) throws  BoardNotFound {
        Board board = boardsService.updateBoard(boardUpdateRequest, boardId);
        return new ResponseEntity<>(board, HttpStatus.ACCEPTED);
    }

//    @PutMapping("/")
    @DeleteMapping("/deleteMultipleBoards")
    public ResponseEntity<?> deleteMultipleBoards(@Valid @RequestBody List<String> ids) {
        boardsService.deleteMultipleBoards(ids);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PutMapping("/updateAllBoards/{boardId}")
    public ResponseEntity<Boards> updateUser(@Valid @RequestBody BoardsUpdateRequest boardUpdateRequest, @PathVariable String boardId) throws UserNotFound, BoardNotFound {
        Boards boards = boardsService.updateBoard(boardUpdateRequest, boardId);
        return new ResponseEntity<>(boards, HttpStatus.ACCEPTED);
    }
}
