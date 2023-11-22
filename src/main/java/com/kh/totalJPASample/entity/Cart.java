package com.kh.totalJPASample.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "cart")
public class Cart {
    @Id
    @Column(name = "cart_id") // 자동으로 생성. 사용해도 되고 안해도 됨
    @GeneratedValue(strategy = GenerationType.AUTO)

    private Long id;
    private String cartName;
    @OneToOne // 회원 엔티티와 일대일 매핑
    @JoinColumn(name = "member_id") // 조인 걸 컬럼
    private Member member;


}
