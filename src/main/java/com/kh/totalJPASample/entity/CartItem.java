package com.kh.totalJPASample.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter
@Table(name="cart_item")
public class CartItem {
    @Id
    @GeneratedValue(strategy =  GenerationType.AUTO)
    @Column(name = "cart_item_id")
    private Long id;

    @ManyToOne // 카트아이템 입장에서는 many to one, 하나의 장바구니에 여러 상품 담을 수 있음(다대일 관계)
    @JoinColumn(name="cart_id") // 카트아이템이 주인
    private Cart cart;

    @ManyToOne
    @JoinColumn(name = "item_id")
    private Item item;// 하나의 아이템은 여러 장바구니에 상품으로 담길 수 있음(다대일 관계)
}
