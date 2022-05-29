package com.study.mvc.oauth2;

import com.study.mvc.domain.Member.Member;
import com.study.mvc.domain.Member.SessionMember;
import com.study.mvc.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.Collections;

@Service
@Slf4j
@RequiredArgsConstructor
public class CustomOauth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {
    private final MemberRepository memberRepository;
    private final HttpSession httpSession;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2UserService oAuth2UserService = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = oAuth2UserService.loadUser(userRequest);

        //현재 클라이언트 서비스를 구분하기 위한 값 {registrationId='google'}
        String registrationId = userRequest.getClientRegistration().getRegistrationId();

        // OAuth2 로그인시 키 값 서비스 별로 키 값이 달라 해당 메서드로 값을 받음
        String userNameAttributeName = userRequest.getClientRegistration().getProviderDetails().getUserInfoEndpoint().getUserNameAttributeName();
        //OAuth2 로그인 후 가져온 OAuth2User의 attribute를 OAuth2Attributes에 담아줌
        OAuth2Attributes attributes = OAuth2Attributes.of(registrationId, userNameAttributeName, oAuth2User.getAttributes());

        Member member = saveOrUpdate(attributes);
        httpSession.setAttribute("member", new SessionMember(member));

        log.info(attributes.getAttributes().toString());

        return new DefaultOAuth2User(Collections.singleton(new SimpleGrantedAuthority(member.getRole())),
                attributes.getAttributes(),
                attributes.getNameAttributeKey());
    }

    private Member saveOrUpdate(OAuth2Attributes attributes) {
        Member member = memberRepository.findByEmail(attributes.getEmail())
                .map(entity -> entity.update(attributes.getName(), attributes.getPicture()))
                .orElse(attributes.toMember());

        return memberRepository.save(member);
    }
}
