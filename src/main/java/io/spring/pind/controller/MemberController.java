package io.spring.pind.controller;

import io.spring.pind.dto.MemberDTO;
import io.spring.pind.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/member")
@Log4j2
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/{member_id}")
    public ResponseEntity<MemberDTO> getMember(@PathVariable("member_id") Long memberId){
        MemberDTO memberDTO = memberService.getMember(memberId);
        return new ResponseEntity<>(memberDTO, HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<Map> register(@RequestBody MemberDTO memberDTO){
        memberService.register(memberDTO);
        Map<String, String> result = new HashMap<>();
        result.put("memberName", memberDTO.getName());
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PutMapping("")
    public ResponseEntity<Map> modify(@RequestBody MemberDTO memberDTO){
        memberService.modify(memberDTO);
        Map<String, String> result = new HashMap<>();
        result.put("memberName", memberDTO.getName());
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @DeleteMapping("/{member_id}")
    public ResponseEntity<Map> delete(@PathVariable("member_id") Long memberId){
        MemberDTO memberDTO = memberService.getMember(memberId);
        memberService.remove(memberId);
        Map<String, String> result = new HashMap<>();
        result.put("memberName", memberDTO.getName());
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    // 웹 페이지 요청 (추후 추가)
//    @GetMapping("/register")
//    public String register(){
//        return "member/register";
//    }

//    @GetMapping("/modify")
//    public String modify(Model model){
//        return "member/modify";
//    }

}