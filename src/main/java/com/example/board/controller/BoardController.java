package com.example.board.controller;


import com.example.board.entity.Board;
import com.example.board.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.Clock;

@Controller
public class BoardController {

    @Autowired
    private BoardService boardService;

    @GetMapping("/board/write")  //localhost:8080/board/write
    public String boardWirte(){

        return "boardWrite";
    }

    @PostMapping("/board/writepro")
    public String boardwritepro(Board board){

        boardService.boardWrite(board);
        return "ss";

    }

    @GetMapping("board/list")
    public String boardList(Model model){

        model.addAttribute("list",boardService.boardList());

        return "boardList";
    }

}
