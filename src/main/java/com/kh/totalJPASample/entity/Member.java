package com.kh.totalJPASample.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity // 클래스 위에 선언하여 이 클래스가 엔티티임을 알려줌. JPA에서 정의된 필드를 바탕으로 DB에 테이블 생성해줌
@Table(name= "member") // 테이블 이름 지정
@Getter @Setter @ToString // 롬복 사용해서 메소드 자동 생성
public class Member {
    @Id//주요키 역할하는 어노테이션
    @GeneratedValue(strategy = GenerationType.AUTO)
    // 주요 키 값 자동 생성 전략을 설정하는 애노테이션. AUTO는 스프링 부트에서 자동으로 생성한다. 이걸 더 많이 사용//Identity: db에 종속되는 애
    @Column(name = "member_id")
    private Long id;//프라이머리 키이든 아니든 이건 무조건 넣는게 편하다. 고유값. 자동증가값으로
    private String userId;
    @Column(nullable = false) // NULL 허용하지 않음
    private String name;
    private String password;
    @Column(unique = true)// 이메일 중복값 허용 안함(제약조건)
    private String email;
    private LocalDateTime regDate;//두번이 들어가야하는 이유가 있나??
    @PrePersist//JPA 엔터티 객체가 저장되기 전에 실행되는 메소드를 지정하는 애노테이션
    public void prePersist(){
        regDate = LocalDateTime.now(); }// (가입일 db에 넣기 전에)객체가 영속성을 가지기 전에 현재 날짜와 시간을 가입일에 설정

}
