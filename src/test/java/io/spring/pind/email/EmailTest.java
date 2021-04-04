package io.spring.pind.email;

import io.spring.pind.dto.MemberDTO;
import io.spring.pind.entity.Member;
import io.spring.pind.message.EmailMessage;
import io.spring.pind.repository.MemberRepository;
import io.spring.pind.service.EmailService;
import io.spring.pind.service.MemberService;
import io.spring.pind.service.MemberServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.IntStream;

@SpringBootTest
public class EmailTest {

    @Autowired
    MemberService memberService;

    @Autowired
    EmailService emailService;

    @Autowired
    MemberRepository memberRepository;

    @Test
    @Transactional
    @Rollback(false)
    public void 회원등록(){
        MemberDTO memberDTO = MemberDTO.builder().email("kangjm222222@naver.com").name("강재민").password("qhdks123!").build();
        Long memberId = memberService.register(memberDTO);
        System.out.println(memberId);
        memberDTO = memberService.getMember(memberId);
        EmailMessage em = new EmailMessage();
        try {
            emailService.sendMail(memberDTO.getEmail(), "[PIND 이메일 인증]", em.AuthMailMessage(memberDTO));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void 회원조회(){
        System.out.println(memberService.getMember(1l));
    }

    @Test
    public void 회원전체조회(){
        List<Member> list = memberRepository.findAll();
        list.stream().forEach(member -> {
            System.out.println(memberService.entitiesToDTO(member));
        });
    }
}
