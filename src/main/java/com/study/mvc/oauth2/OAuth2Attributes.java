package com.study.mvc.oauth2;

import com.study.mvc.domain.Member.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class OAuth2Attributes {
    private Map<String, Object> attributes;
    private String nameAttributeKey;
    private String name;
    private String email;
    private String picture;

    public static OAuth2Attributes of(String registrationId, String userNameAttributeName, Map<String, Object> attributes) {
        if(registrationId.equals("google")) {
            return ofGoogle(userNameAttributeName, attributes);
        } else {
            //naver, kakao ... 추가
            return null;
        }
    }

    private static OAuth2Attributes ofGoogle(String userNameAttributeName, Map<String, Object> attributes) {
        return OAuth2Attributes.builder()
                .attributes(attributes)
                .nameAttributeKey(userNameAttributeName)
                .name((String) attributes.get("name")) // toString 하면 값이 다른가?
                .email((String) attributes.get("email"))
                .picture((String) attributes.get("picture"))
                .build();
    }

    public Member toMember() {
        return Member.builder()
                .name(name)
                .email(email)
                .picture_url(picture)
                .build();
    }
}
