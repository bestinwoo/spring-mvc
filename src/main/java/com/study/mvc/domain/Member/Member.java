package com.study.mvc.domain.Member;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String email;
    private String picture_url;
    private String role = "ROLE_USER";

    public Member update(String name, String picture) {
        this.name = name;
        this.picture_url = picture;
        this.role = "ROLE_USER";
        return this;
    }
}
