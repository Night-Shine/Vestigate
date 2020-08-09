package com.nightshine.vestigate.controller.board;

import com.nightshine.vestigate.exception.board.BoardNotFound;
import com.nightshine.vestigate.model.board.Board;
import com.nightshine.vestigate.payload.request.board.BoardUpdateRequest;
import com.nightshine.vestigate.service.board.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/boards")
public class BoardController {
    @Autowired
    private BoardService boardService;

    @PostMapping("/addBoard")
    private ResponseEntity<?> addBoard(@RequestBody Board board, @RequestParam UUID projectId) throws Throwable {
         boardService.addBoards(projectId,board);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();

    }


    @DeleteMapping("/deleteBoard")
    private ResponseEntity<?> deleteTeam(@RequestParam UUID projectId,@RequestParam UUID boardId) throws Exception {
        boardService.deleteBoard(projectId,boardId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

//    @PutMapping("/")
    @DeleteMapping("/deleteMultipleBoards")
    public ResponseEntity<?> deleteMultipleBoards(@Valid @RequestBody List<UUID> ids,@RequestParam UUID projectId) throws Exception {
        boardService.deleteMultipleBoards(projectId,ids);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PutMapping("/updateAllBoards/{boardId}")
    public ResponseEntity<Board> updateUser(@Valid @RequestBody BoardUpdateRequest boardUpdateRequest, @PathVariable UUID boardId) throws  BoardNotFound {
        Board board = boardService.updateBoard(boardUpdateRequest, boardId);
        return new ResponseEntity<>(board, HttpStatus.ACCEPTED);
    }
}
