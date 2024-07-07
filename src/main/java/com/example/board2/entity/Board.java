package com.example.board2.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;


//롬복, 콜트롤러에서 호출시 자동으로 나오게 하기

@Data
@Entity
public class Board {    //테이블 이름
  
    
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Id
    private Long id;

    private String title;

    private String content;

    private String filename;

    private String filepath;
    
}
