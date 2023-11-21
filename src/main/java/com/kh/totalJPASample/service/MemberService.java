package com.kh.totalJPASample.service;

import com.kh.totalJPASample.dto.MemberDto;
import com.kh.totalJPASample.entity.Member;
import com.kh.totalJPASample.repository.MemberRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.List;

@Service // 해당 객체를 빈으로 등록
@RequiredArgsConstructor // 매개 변수가 전부 포함된 생성자를 자동 생성 해 줌
public class MemberService {
    private final MemberRepository memberRepository;// MemberRepository를 주입받아 사용하기 위한 생성자

    // 회원 등록 메소드
    public boolean saveMember(MemberDto memberDto) {
        // 이미 등록된 회원인지 확인하는 쿼리
        boolean isReg = memberRepository.existsByEmail(memberDto.getEmail());
        if(isReg) return false;// 이미 가입한 회원이면 등록하지 않고 false 반환

        Member member = new Member();//  여기는 의존성 주입 하지 않는 이유: 독립적으로 멤버가 생성되어야 함.새로운 Member 엔티티 객체 생성
        member.setEmail(memberDto.getEmail());
        member.setPassword(memberDto.getPassword());
        member.setName(memberDto.getName());
        memberRepository.save(member); // 생성한 회원 저장
        return true; // 회원등록 성공시 true 반환
    }
    // 회원 전체 조회 - 외부에서 호출필요하니 public
    public List<MemberDto> getMemberList() {
        List<MemberDto> memberDtos = new ArrayList<>();
        List<Member> members = memberRepository.findAll();//selectAll과 같음, 모든 회원 조회
        for (Member member : members) {// 자동순회해서 멤버 객체를 아래에 담음
            memberDtos.add(convertEntityToDto(member));//memberDtos라는 리스트에 member를 담아줌 //Member 엔티티를 MemberDto로 변환하여 리스트에 추가
        }
        return memberDtos; // 변환된 MemberDto 리스트 반환
    }

    // 페이지네이션 조회
    public List<MemberDto> getMemberList(int page, int size){ // 매개변수가 같으면 위와 같은 이름이어도 오버라이딩 걸림
        Pageable pageable = PageRequest.of(page, size);
        List<MemberDto> memberDtos = new ArrayList<>();
        List<Member> members= memberRepository.findAll(pageable).getContent();
        for(Member member : members){
            memberDtos.add(convertEntityToDto(member));
        }
        return memberDtos;

    }
    // 페이지 수 조회
    public int getMemberPage(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return memberRepository.findAll(pageable).getTotalPages();
    }
    // 회원 상세 조회
    public MemberDto getMemberDetail(String email){

        Member member = memberRepository.findByEmail(email).orElseThrow(
                ()->new RuntimeException("해당회원이 존재하지 않습니다. ")
        );
        return convertEntityToDto(member);
    }



    // 회원 엔티티를 DTO로 변환하는 메소드 만들기 - 내부에서만 호출하니 private
    private MemberDto convertEntityToDto(Member member){
        MemberDto memberDto = new MemberDto();
        memberDto.setEmail(member.getEmail());
        memberDto.setPassword(member.getPassword());
        memberDto.setName(member.getName());
        memberDto.setRegDate(member.getRegDate());
        return memberDto; // 변환된 MemberDto 반환

    }

}
