package io.spring.pind.security.service;

import io.spring.pind.entity.FromSocial;
import io.spring.pind.entity.Member;
import io.spring.pind.repository.MemberRepository;
import io.spring.pind.security.dto.AuthMemberDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Log4j2
@Service
@RequiredArgsConstructor
public class OAuthService extends DefaultOAuth2UserService{

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        log.info("userRequest: " + userRequest);

        String clientName = userRequest.getClientRegistration().getClientName();

        log.info("clientName: " + clientName);
        log.info(userRequest.getAdditionalParameters());

        OAuth2User oAuth2User = super.loadUser(userRequest);

        oAuth2User.getAttributes().forEach((k, v) -> {
            log.info(k + ":" + v);
        });

        String email = null;
        if (clientName.equals("Google")){
            email = oAuth2User.getAttribute("email");
        }
        log.info("Email: " + email);

        Member member = saveSocialMember(email);
        List<SimpleGrantedAuthority> role = new ArrayList<>();
        role.add(new SimpleGrantedAuthority("ROLE_USER"));
        AuthMemberDTO authMember = new AuthMemberDTO(
                member.getEmail(),
                member.getPassword(),
                FromSocial.GOOGLE,
                role,
                oAuth2User.getAttributes());
        authMember.setName(member.getName());

        return authMember;
    }

    private Member saveSocialMember(String email){
        Optional<Member> result = memberRepository.findByEmail(email);

        if (result.isPresent()){
            return result.get();
        }
        Member member = Member.builder().email(email)
                .name(email)
                .password(passwordEncoder.encode("default"))
                .fromSocial(FromSocial.GOOGLE)
                .build();
        memberRepository.save(member);
        return member;
    }
}
