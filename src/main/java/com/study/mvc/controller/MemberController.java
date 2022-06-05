package com.study.mvc.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class MemberController {
    @GetMapping("/member/login")
    public String login() {
        return "/member/login";
    }

    @GetMapping("/login/oauth2/code/google")
    public String googleLogin() {
        return "/";
    }
}
