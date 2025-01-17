package com.example.board2.service;

import java.io.File;
// import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.board2.entity.Board;
import com.example.board2.repository.BoardRepository;

@Service
public class BoardService {

    @Autowired  //di, bean 알아서 주입
    private BoardRepository boardRepository;


    // 글 작성 처리
    public void write(Board board, MultipartFile file) throws Exception{

        String projectPath = System.getProperty("user.dir") + "\\src\\main\\resources\\static\\files";

        UUID uuid = UUID.randomUUID();

        String fileName = uuid + "_" + file.getOriginalFilename();

        File saveFile = new File(projectPath, fileName);

        file.transferTo(saveFile);

        board.setFilename(fileName);
        board.setFilepath("/files/" + fileName);

        boardRepository.save(board);
    }

    // public void write(Board board) {

    //     boardRepository.save(board);
    // }


    // public void list(Board board) {

    //     boardRepository.findAll();
    // }


    // 노페이지
    // public List<Board> boardList() {
    //     return boardRepository.findAll();
    // }

    public Page<Board> boardList(Pageable pageable) {
        return boardRepository.findAll(pageable);
    }

    public Board boardView(Integer id) {
        return boardRepository.findById(id).get();
    }

    public Page<Board> boardSearchList(String searchKeyword, Pageable pageable) {
        return boardRepository.findByTitleContaining(searchKeyword, pageable);
    }

}
