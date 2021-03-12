package io.spring.pind.email;

import io.spring.pind.dto.MemberDTO;
import io.spring.pind.message.EmailMessage;
import io.spring.pind.service.EmailService;
import io.spring.pind.service.MemberService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
public class EmailTest {

    @Autowired
    MemberService memberService;

    @Autowired
    EmailService emailService;

    @Test
    @Transactional
    void 회원등록(){
        MemberDTO memberDTO = MemberDTO.builder().email("kangjm2@naver.com").name("강재민").password("qhdks123!").build();
        Long memberId = memberService.register(memberDTO);
        memberDTO = memberService.getMember(memberId);
        EmailMessage em = new EmailMessage();
        try {
            emailService.sendMail(memberDTO.getEmail(), "[PIND 이메일 인증]", em.AuthMailMessage(memberDTO));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
