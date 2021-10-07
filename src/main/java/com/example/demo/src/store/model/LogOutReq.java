package com.example.demo.src.store.model;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class LogOutReq {
    private Integer userIdx;
    private String userJwtToken;        // can null
}
