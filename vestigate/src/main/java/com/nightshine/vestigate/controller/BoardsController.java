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
import java.util.UUID;

@RestController
@RequestMapping("/boards")
public class BoardsController {
    @Autowired
    private BoardsService boardsService;

    @PostMapping("/addBoard")
    private ResponseEntity<?> addBoard(@RequestBody Board board, @RequestParam UUID projectId) throws Throwable {
         boardsService.addBoards(projectId,board);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();

    }


    @DeleteMapping("/deleteBoard")
    private ResponseEntity<?> deleteTeam(@RequestParam UUID projectId,@RequestParam UUID boardId) throws Exception {
        boardsService.deleteBoard(projectId,boardId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

//    @PutMapping("/")
    @DeleteMapping("/deleteMultipleBoards")
    public ResponseEntity<?> deleteMultipleBoards(@Valid @RequestBody List<UUID> ids,@RequestParam UUID projectId) throws Exception {
        boardsService.deleteMultipleBoards(projectId,ids);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PutMapping("/updateAllBoards/{boardId}")
    public ResponseEntity<Board> updateUser(@Valid @RequestBody BoardsUpdateRequest boardUpdateRequest, @PathVariable UUID boardId) throws  BoardNotFound {
        Board board = boardsService.updateBoard(boardUpdateRequest, boardId);
        return new ResponseEntity<>(board, HttpStatus.ACCEPTED);
    }
}
