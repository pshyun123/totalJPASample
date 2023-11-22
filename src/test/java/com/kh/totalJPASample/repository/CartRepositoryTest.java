package com.kh.totalJPASample.repository;
import com.kh.totalJPASample.entity.Cart;
import com.kh.totalJPASample.entity.Member;
import lombok.RequiredArgsConstructor;
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

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest // 스프링 컨텍스트를 로드하여 테스트 환경 설정
@Transactional // 데이터베이스의 논리적인 작업 단위, 모두 성공하는 경우가 아니면 롤백, 테스트쪽은 모두 성공해도 롤백함!
@Slf4j   // 로딩 데이터 처리를 위해 사용, 1. 화면에 찍어주는 용도. 2. 로그 정보 남기기 - 지금 당장은 필요 없지만 얘가 로그 남기기 위해 사용(해킹, 사용자가 어떤 경로로 오류 남겼는지등)
@TestPropertySource(locations="classpath:application-test.properties") // 테스트에 사용할 프로퍼티 파일의 위치를 지정

class CartRepositoryTest {
    @Autowired //@RequiredArgsConstructor이거 있으면 안써도 됨-롬복이 자동으로.. //의존성 주입
    CartRepository cartRepository;
    @Autowired
    MemberRepository memberRepository;
    @PersistenceContext // JPA의 EntityManager를 주입받음(가끔 주입할 필요 있음-> 언제?)
    EntityManager em;

    // 회원 엔티티 생성
    public Member createMemberInfo() {
        Member member = new Member();
        member.setUserId("jks2024");
        member.setPassword("sphb8250");
        member.setName("곰돌이 사육사");
        member.setEmail("jks2024@gmail.com");
        member.setRegDate(LocalDateTime.now());
        return member;
    }

    @Test
    @DisplayName("장바구니 회원 매핑 조회 테스트")
    public void findCartAndMemberTest(){
        Member member = createMemberInfo();
        memberRepository.save(member); // 회원 1명 저장

        Cart cart = new Cart();
        cart.setCartName("마켓 컬리 장바구니");
        cart.setMember(member);
        cartRepository.save(cart);

        //강제 플러싱
        em.flush(); // 연속성 컨텍스트에 데이터 저장 후 트랜잭션이 끝날 때 flush() 호출하여 데이터베이스에 반영
        em.clear(); // 영속성 켄텍스트를 비움. 쓰고 빨리 읽으려면 무언가 사용할때 사용
        Cart saveCart = cartRepository.findById(cart.getId()).orElseThrow(EntityNotFoundException::new); // 없으면 예외 처리, 바로 읽기 위함
        System.out.println(saveCart);
    }

}