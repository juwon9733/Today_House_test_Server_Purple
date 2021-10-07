package com.example.demo.src.community.model;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class GetCommentContents {
    private int commentIdx;
    private int userIdx;
    private int articleIdx;
    private int articleMediaIdx;
    private String comment;
    private int numHeart;
    private String createdAt;
    private String updatedAt;
}
