package com.kh.totalJPASample.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
// data transfer object : 계층간 데이터를 전송하기 위한 객체. 주로 프론트엔드와 json으로 통신하기 위한 객체
// 요청과 응답에 대한 객체
public class MemberDto {
    private String email;
    private String password;
    private String name;
    private LocalDateTime regDate;
}
