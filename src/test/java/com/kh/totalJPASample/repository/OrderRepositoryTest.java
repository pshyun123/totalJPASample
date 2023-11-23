package com.kh.totalJPASample.repository;

import com.kh.totalJPASample.constant.ItemSellStatus;
import com.kh.totalJPASample.entity.Item;
import com.kh.totalJPASample.entity.Member;
import com.kh.totalJPASample.entity.Order;
import com.kh.totalJPASample.entity.OrderItem;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Transactional
@TestPropertySource(locations="classpath:application-test.properties") // 테스트에 사용할 프로퍼티 파일의 위치를 지정
@Slf4j
class OrderRepositoryTest {
    @Autowired
    OrderRepository orderRepository;
    @Autowired
    ItemRepository itemRepository;
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    OrderItemRepository orderItemRepository;
    @PersistenceContext
    EntityManager em;
    public Item createItem(){
        Item item = new Item();
        item.setItemName("테스트 상품");
        item.setPrice(10000);
        item.setItemDetail("테스트 상품 상세 설명");
        item.setItemSellStatus(ItemSellStatus.SELL);
        item.setStockNumber(100);
        return item;
    }
    @Test
    @DisplayName("영속성 전이 테스트")
    public void cascadeTest(){
        Order order = new Order(); // 주문
        for(int i = 0; i<3; i++){
            Item item = this.createItem();
            itemRepository.save(item);
            OrderItem orderItem = new OrderItem(); // 주문 아이템 생성
            orderItem.setItem(item);
            orderItem.setCount(10);
            orderItem.setOrderPrice(1000);
            orderItem.setOrder(order);
            order.getOrderItemList().add(orderItem);
        }
        //엔티티를 저장하면서 DB에 반영
        orderRepository.saveAndFlush(order);//엔티티 매니저가 원래 db에 반영해주는데, 테스트 파일에서는 저장하자마자 바로 반영하려면 saveAndFlush 써주자.
        em.clear(); // 영속성 상태를 초기화
        //주문 엔티티 조회
        Order saveOrder = orderRepository.findById(order.getId()).orElseThrow(EntityNotFoundException::new);// 엔티티 예외처리
        //Order 객체의 orderitem 개수 3개 인지 확인
//        assertEquals(3, saveOrder.getOrderItemList().size());
        log.warn(String.valueOf(saveOrder.getOrderItemList().size()));

    }
    public Order createOrder() {
        Order order = new Order();

        for(int i=0; i<3; i++){
            Item item = createItem();// Item item = new Item();을 세번째하는것과 같다.
            itemRepository.save(item);
            OrderItem orderItem =new OrderItem();
            orderItem.setItem(item);
            orderItem.setCount(10);
            orderItem.setOrderPrice(1000);
            orderItem.setOrder(order);
            order.getOrderItemList().add(orderItem);
        }
        Member member = new Member(); // 생성
        member.setName("곰도링");
        member.setEmail("jks2024@gmail.com");
        memberRepository.save(member);

        order.setMember(member);
        orderRepository.save(order);//db에 저장하기 위해 save
        return order;

    }
    @Test
    @DisplayName("고아 객체 제거 테스트")
    public void orphanRemovalTest(){
        Order order = this.createOrder();
        order.getOrderItemList().remove(0);//0번 배열 지움, 고아객체가 된다. db에 생성되어있는 것도 지워짐, 연관관계 끊김
        em.flush();
    }

    @Test
    @DisplayName("지연 로딩 테스트")
    public void lazyLoadingTest(){
        Order order = this.createOrder();
        Long orderItemId = order.getOrderItemList().get(0).getId();//
        em.flush();
        em.clear();// 즉시 실행
        OrderItem orderItem = orderItemRepository.findById(orderItemId).orElseThrow(EntityNotFoundException::new);
        log.warn(String.valueOf(orderItem.getOrder().getClass()));
        log.warn("===============================================");
        log.warn(String.valueOf(orderItem.getOrder().getOrderDate()));
        log.warn("===============================================");


    }

}