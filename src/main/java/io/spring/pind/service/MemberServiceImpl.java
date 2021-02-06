package io.spring.pind.service;

import io.spring.pind.dto.MemberDTO;
import io.spring.pind.entity.Member;
import io.spring.pind.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Log4j2
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {
    private final MemberRepository memberRepository;

    @Override
    public Long register(MemberDTO memberDTO) {
        Member member = dtoToEntity(memberDTO);
        memberRepository.save(member);
        return member.getId();
    }

    @Override
    public MemberDTO getMember(Long mno) {
        Member member = memberRepository.getOne(mno);
        MemberDTO memberDTO = entitiesToDTO(member);
        return memberDTO;
    }

    @Override
    public void modify(MemberDTO memberDTO) {
        Optional<Member> result = memberRepository.findById(memberDTO.getId());

        if(result.isPresent()){
            Member member = result.get();
            member.changeEmail(memberDTO.getEmail());
            member.changeName(memberDTO.getName());
            memberRepository.save(member);
        }
    }

    @Override
    public void remove(Long mno) {
        memberRepository.deleteById(mno);
    }
}
