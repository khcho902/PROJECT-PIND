package io.spring.pind.repository;

import io.spring.pind.entity.Member;
import io.spring.pind.entity.Project;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.stream.IntStream;

@SpringBootTest
public class MemberRepositoryTest {
    @Autowired
    private MemberRepository memberRepository;

    PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    @Test
    public void insertMembers(){
        IntStream.rangeClosed(1, 100).forEach(i -> {
            Member member = Member.builder()
                    .email(i + "@naver.com")
                    .name(i + "human")
                    .password(passwordEncoder.encode(String.valueOf(i))).build();
            memberRepository.save(member);
        });
    }
}
