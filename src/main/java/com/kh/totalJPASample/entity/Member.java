package com.kh.totalJPASample.entity;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name= "member")
@Getter @Setter @ToString
public class Member {
    //주요키 역할@
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    //AUTO: 스프링 부트에서 자동으로 생성. 이걸 더 많이 사용//Identity: db에 종속되는 애
    private Long id;//프라이머리 키이든 아니든 이건 무조건 넣는게 편하다. 고유값. 자동증가값으로
    @Column(nullable = false) // NULL 허용하지 않음
    private String name;
    private String password;
    @Column(unique = true)// 이메일 중복값 허용 안함(제약조건)
    private String email;
    private LocalDateTime regDate;
    @PrePersist
    public void prePersist(){
        regDate = LocalDateTime.now(); // 가입일 db에 넣기전에 영속성이 있는 날짜 값이 들어간다.
    }

}
