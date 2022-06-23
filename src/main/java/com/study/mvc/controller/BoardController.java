package com.study.mvc.controller;

import com.study.mvc.domain.Member.Member;
import com.study.mvc.domain.Member.SessionMember;
import com.study.mvc.domain.board.Board;
import com.study.mvc.repository.BoardRepository;
import com.study.mvc.repository.MemberRepository;
import com.study.mvc.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionAttributeStore;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class BoardController {
    private final BoardRepository boardRepository;
    private final MemberRepository memberRepository;
    @GetMapping("/")
    public String list(Model model) {
        List<Board> boards = boardRepository.findAll();
        model.addAttribute("boards", boards);
        return "/index";
    }

    @GetMapping("/board/form")
    public String form(Model model, HttpSession session){
        SessionMember sessionMember = (SessionMember) session.getAttribute("member");
        memberRepository.findByName(sessionMember.getName()).ifPresentOrElse(m -> {
            Board emptyBoard = Board.builder().writer(m).build();
            model.addAttribute("board", emptyBoard);

        }, () -> {
        throw new IllegalStateException("로그인 정보가 없습니다.");
        });
        return "board/form";
    }

    @PostMapping("/board/form")
    public String writeBoard(@ModelAttribute("board") Board board) {
        boardRepository.save(board);
        return "redirect:/";
    }
}
