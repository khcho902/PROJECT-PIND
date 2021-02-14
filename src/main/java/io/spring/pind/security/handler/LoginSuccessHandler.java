package io.spring.pind.security.handler;

import io.spring.pind.entity.FromSocial;
import io.spring.pind.security.dto.AuthMemberDTO;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.persistence.criteria.From;
import javax.servlet.ServletException;
        import javax.servlet.http.HttpServletRequest;
        import javax.servlet.http.HttpServletResponse;
        import java.io.IOException;

@Log4j2
public class LoginSuccessHandler implements AuthenticationSuccessHandler {
    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    private PasswordEncoder passwordEncoder;

    public LoginSuccessHandler(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        log.info("onAuthenticationSuccess");

        AuthMemberDTO authMember = (AuthMemberDTO)authentication.getPrincipal();
        FromSocial fromSocial = authMember.getFromSocial();
        // OAuth 로그인시 리다이렉트 등 별도의 핸들러가 필요할 경우 사용.
        // 가령, OAuth 첫 로그인시 이름, 주소 등 설정이 필요한 경우.
//        if (fromSocial == FromSocial.GOOGLE) {
//            redirectStrategy.sendRedirect(request, response, "/member/modify?from=google");
//        }
    }
}
