package com.kh.totalJPASample.entity;

import com.kh.totalJPASample.constant.OrderStatus;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue // 기본 생성 전략은 auto
    @Column(name = "order_id")
    private Long id;

    @ManyToOne // 회원이 주인
    @JoinColumn(name = "member_id")
    private Member member;

    private LocalDateTime orderDate; // 주문일

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    private LocalDateTime regTime;
    private LocalDateTime updateTime;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL) // 연관 관계의 주인이 아님을 표시함 -> 아까 ManyToOne에서는 객체 만들었지만 여기서는 안만들어도 된다.
    private List<OrderItem> orderItemList = new ArrayList<>();// 아이템이 만들어진 정보가 넘어옴
}
