package com.kh.totalJPASample.controller;

import com.kh.totalJPASample.dto.MemberDto;
import com.kh.totalJPASample.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.kh.totalJPASample.utils.Common.CORS_ORIGIN;

@Slf4j//log f4 , 로그 기록 & 화면에 출력하기 . 다운되기 직전까지 로그 기록이 남아있어야함
@CrossOrigin(origins = CORS_ORIGIN)
@RestController
@RequestMapping("/users") // 주소 경로
@RequiredArgsConstructor // 생성자 전체 삽입해서 bin에서 MemberService를 삽

public class MemberController {
    private final MemberService memberService; // 의존성 주입. 제어 역전 일어남 스프링 부트가 제어함

    // 회원 등록 (성공,실패니까 true/false)
    @PostMapping("/new")
    public ResponseEntity<Boolean> memberRegister(@RequestBody MemberDto memberDto) {
//        log.info("email : ", memberDto.getEmail());
        boolean isTrue = memberService.saveMember(memberDto);
        return ResponseEntity.ok(isTrue);
    }// RestfulAPI 복습하면 더 잘 이해가능 // 컨트롤러 코드는 길어도 5줄 이하. dto를 entity가
    // 회원 전체 조회
    @GetMapping("/list")
    //List-어레이, 백터, 링크드 모두 포함
    public ResponseEntity<List<MemberDto>> memberList() {
        List<MemberDto>list = memberService.getMemberList();
        return ResponseEntity.ok(list);
    }
    // 회원 상세 조회
    @GetMapping("/detail/{email}")//path에다가 변수 집어넣는 방법
    public ResponseEntity<MemberDto> memberDetail(@PathVariable String email) {
        MemberDto memberDto = memberService.getMemberDetail(email);
        return ResponseEntity.ok(memberDto);
    }// 하나니까 리스트로 만들 필요 없음
}
