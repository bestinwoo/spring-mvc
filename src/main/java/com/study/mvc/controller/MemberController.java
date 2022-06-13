package com.study.mvc.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.security.Principal;

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

    @GetMapping("/member/loginInfo")
    @ResponseBody
    public String loginInfo(Authentication authentication, @AuthenticationPrincipal Principal principal) {
        return "hi";
    }
}
