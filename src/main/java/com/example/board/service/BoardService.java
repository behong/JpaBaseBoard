package com.example.board.service;

import com.example.board.entity.Board;
import com.example.board.repository.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BoardService {

    @Autowired
    private BoardRepository boardRepository;

    // 글 작성
    public void boardWrite(Board board){
        boardRepository.save(board);
    }

    // 리스트
    public List<Board> boardList(){
       return boardRepository.findAll();
    }
    // 게시글 상세
    public Board boardView(Long id){
        return boardRepository.findById(id).get();
    }

    // 글 삭제
    public void boardDelete(Long id){
        boardRepository.deleteById(id);
    }

    // 글 수정

}
