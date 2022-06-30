package com.study.mvc.domain.Member;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;


@NoArgsConstructor
@Getter
public class SessionMember implements Serializable {
    private Long id;
    private String name;
    private String email;
    private String picture;

    public SessionMember(Member member) {
        this.id = member.getId();
        this.name = member.getName();
        this.email = member.getEmail();
        this.picture = member.getPicture_url();
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }
}
