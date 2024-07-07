package com.example.board2.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.board2.entity.Board;
import com.example.board2.repository.BoardRepository;

@Service
public class BoardService {

    @Autowired  //di, bean 알아서 주입
    private BoardRepository boardRepository;


    public void write(Board board) {

        boardRepository.save(board);
    }

    // public void list(Board board) {

    //     boardRepository.findAll();
    // }

    public List<Board> boardList() {
        return boardRepository.findAll();
    }

    public Board boardView(Integer id) {
        return boardRepository.findById(id).get();
    }


}
