package com.example.board2.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;

import com.example.board2.entity.Board;
import com.example.board2.service.BoardService;



@Controller
public class boardController {


    @Autowired
    private BoardService boardService;


    @GetMapping("/board/write")
     public String boardWriteForm() {
        return "boardwrite";
    }


    
    // @PostMapping("/board/writepro")
    // public String boardWritePro(String title, String content) {  //엔티티가 없어서 필드로 받음
    //     System.out.println(title);
    //     System.out.println(content);
    //     System.out.println("-------------------------");
        
    //     return "";
    // }


    @PostMapping("/board/writepro")
    public String boardWritePro(Board board, Model model, MultipartFile file) throws Exception{     //엔티티 있으면 클래스명
        System.out.println(board.getTitle());

        boardService.write(board, file);
        
        model.addAttribute("message", "글 작성 완료...");
        model.addAttribute("searchUrl", "/board/list");

        return "message";
    }


    // @GetMapping("/board/list")
    // public String boardList( ) {

    //     System.out.println("========================");

    //     return "boardlist";
    // }


    // 노 페이지 처리
    // @GetMapping("/board/list")
    // public String boardList(Model model) {

    //     model.addAttribute("list", boardService.boardList());  //object attribute 로 해야됨

    //     return "boardlist";
    // }


    // 페이지 완료
    // @GetMapping("/board/list")
    // public String boardList(Model model, @PageableDefault(page = 0, size = 10, sort = "id", direction = Direction.DESC) Pageable pageable) {    
    //     //domain 이 나오는걸로 선택

    //     model.addAttribute("list", boardService.boardList(pageable));  
    //     //object attribute 로 해야됨

    //     return "boardlist";
    // }


    @GetMapping("/board/list")
    public String boardList(Model model, 
                @PageableDefault(page = 0, size = 10, sort = "id", direction = Direction.DESC) Pageable pageable,
                String searchKeyword) {    
        //domain 이 나오는걸로 선택

             Page<Board> list = null;

        if(searchKeyword == null) {
            list = boardService.boardList(pageable);
        }else {
            list = boardService.boardSearchList(searchKeyword, pageable);
        }

        int nowPage = list.getPageable().getPageNumber() + 1;
        int startPage = Math.max(nowPage - 4, 1);
        int endPage = Math.min(nowPage + 5, list.getTotalPages());

        model.addAttribute("list", list);
        model.addAttribute("nowPage", nowPage);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);


        model.addAttribute("list", list);  
        //object attribute 로 해야됨

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

        boardService.write(boardTemp, file);

        return "redirect:/board/list";

    }

}
