package com.example.board.controller;


import com.example.board.entity.Board;
import com.example.board.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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
    public String boardwritepro(Board board, Model model, MultipartFile file) throws IOException {

        boardService.boardWrite(board,file);

        model.addAttribute("message","등록 성공");
        model.addAttribute("replaceUrl" ,"/board/list");

        return "message";

    }

    @GetMapping("board/list")
    public String boardList(Model model,@PageableDefault(page = 0,size = 10,sort = "id",direction = Sort.Direction.DESC) Pageable pageable){
        Page<Board> list = boardService.boardList(pageable);

        int nowPage= list.getPageable().getPageNumber() + 1;
        //현재 페이지가 1인경우 -4 를 하면  // 1보다 작을 경우 오른쪽
        int startPage = Math.max(nowPage -4 ,1);
        // 10 페이지 뿐이 없는데  // 전체 페이지를 넘지 않게..
        int endPage = Math.min(nowPage + 5,list.getTotalPages());

        model.addAttribute("list",list);
        model.addAttribute("nowPage",nowPage);
        model.addAttribute("startPage",startPage);
        model.addAttribute("endPage",endPage);

        return "boardList";
    }

    @GetMapping("board/view")   //board/view?id=1
    public String boardView(Model model,Long id){

        model.addAttribute("board",boardService.boardView(id));

        return "boardView";
    }

    @GetMapping("board/delete")
    public String boardDelete(Model model,Long id){
        boardService.boardDelete(id);
        return "redirect:/board/list";
    }

    @GetMapping("board/modify/{id}")
    public String boardModify(@PathVariable("id") Long id ,Model model){

        model.addAttribute("boardView", boardService.boardView(id));

        return "boardModify";
    }

    @PostMapping("/board/update/{id}")
    public String boardUpdate(@PathVariable("id") Long id,Board board,Model model,MultipartFile file) throws IOException {

        Board boardTemp = boardService.boardView(id);
        boardTemp.setTitle(board.getTitle());
        boardTemp.setContent(board.getContent());

        boardService.boardWrite(boardTemp,file);

        model.addAttribute("message","수정 성공");
        model.addAttribute("replaceUrl" ,"/board/list");

        return "message";

    }

}
