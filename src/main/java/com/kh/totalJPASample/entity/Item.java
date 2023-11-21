package com.kh.totalJPASample.entity;

import com.kh.totalJPASample.constant.ItemSellStatus;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@Entity // primary 키가 있어야 실행가능 클래스를 엔티티로 선언
@Table(name = "item") // 엔티티와 매핑할 테이블 지정(사실 지정 안해도 됨)
public class Item {
    @Id // primary 키가 있어야
    @Column (name = "item_id") // id가 item_id으로 이름 지어짐. 필드와 컬럼을 매핑
    @GeneratedValue(strategy = GenerationType.AUTO) // JPA가 자동으로 생성전략 결정
    private Long id; // 상품 코드

    @Column (nullable = false, length = 50) // null 허용하지 않고 길이를 50바이트로 제한
    private String itemName; // 상품명 item_name

    @Column(name = "price", nullable = false)
    private int price; // 가격

    @Column(nullable = false)
    private int stockNumber; // 재고 수량

    @Lob
    @Column(nullable = false)
    private String itemDetail; // 상품 설명

    @Enumerated(EnumType.STRING) // enum으로 정의된 값을 문자열로 DB에 저장
    private ItemSellStatus itemSellStatus; //
    private LocalDateTime regTime; // 등록 시간
    private LocalDateTime updateTime; // 수정시간

}
