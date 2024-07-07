package com.example.board2.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
// import org.springframework.web.bind.annotation.ResponseBody;
// import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.example.board2.entity.Board;
import com.example.board2.service.BoardService;



@Controller
public class boardController {


    @Autowired
    private BoardService boardService;

    @GetMapping("/board/write")
    // @ResponseBody
    public String boardWriteForm() {
        return "boardWrite";
    }


    
    // @PostMapping("/board/writepro")
    // public String boardWritePro(String title, String content) {  //엔티티가 없어서 필드로 받음
    //     System.out.println(title);
    //     System.out.println(content);
    //     System.out.println("-------------------------");
        
    //     return "";
    // }


    @PostMapping("/board/writepro")
    public String boardWritePro(Board board) {     //엔티티 있으면 클래스명
        System.out.println(board.getTitle());

        boardService.write(board);
        
        return "----------------";
    }


    // @GetMapping("/board/list")
    // public String boardList( ) {

    //     System.out.println("========================");

    //     return "boardlist";
    // }

    @GetMapping("/board/list")
    public String boardList(Model model) {

        model.addAttribute("list", boardService.boardList());  //object attribute 로 해야됨
        System.out.println("========================");

        return "boardlist";
    }


    @GetMapping("/board/view")
    public String boardView(Model model, Integer id) {

        model.addAttribute("board", boardService.boardView(id));
        
        return "boardview";
    }

    @GetMapping("/board/modify/{id}")
    public String boardModify(@PathVariable("id") Integer id, Model model) {

        model.addAttribute("board", boardService.boardView(id));

        return "boardmodify";
    }
    
    @PostMapping("/board/update/{id}")
    public String boardUpdate(@PathVariable("id") Integer id, Board board, MultipartFile file) throws Exception{

        Board boardTemp = boardService.boardView(id);
        boardTemp.setTitle(board.getTitle());
        boardTemp.setContent(board.getContent());

        return "redirect:/board/list";

    }

}