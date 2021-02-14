package io.spring.pind.security.service;

import io.spring.pind.entity.Member;
import io.spring.pind.repository.MemberRepository;
import io.spring.pind.security.dto.AuthMemberDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Log4j2
@Service
@RequiredArgsConstructor
public class AuthService implements UserDetailsService {
    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Member> result = memberRepository.findByEmail(username);

        if (result.isEmpty())
            throw new UsernameNotFoundException("Check email address.");

        Member member = result.get();

        if (member.getFromSocial() != null)
            throw new UsernameNotFoundException("Login with OAUTH : " + member.getFromSocial());

        List<SimpleGrantedAuthority> role = new ArrayList<>();
        role.add(new SimpleGrantedAuthority("ROLE_USER"));
        AuthMemberDTO authMember = new AuthMemberDTO(
                member.getEmail(),
                member.getPassword(),
                member.getFromSocial(),
                role
        );
        authMember.setName(member.getName());
        return authMember;
    }
}
