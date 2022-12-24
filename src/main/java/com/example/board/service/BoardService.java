package com.example.board.service;

import com.example.board.entity.Board;
import com.example.board.repository.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Service
public class BoardService {

    @Autowired
    private BoardRepository boardRepository;

    // 글 작성
    public void boardWrite(Board board, MultipartFile file) throws IOException {

        String projectPath = System.getProperty("user.dir")+"\\src\\main\\resources\\static\\files";

        // 랜덤ID + 업로드파일명 합쳐 준다
        UUID uuid = UUID.randomUUID();
        String fileName = uuid+"_"+ file.getOriginalFilename();

        // 파일 객체 생성
        File saveFile = new File(projectPath,fileName);
        // 실제 파일 저장
        file.transferTo(saveFile);

        board.setFilename(fileName);
        board.setFilepath("/files/"+fileName);

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
