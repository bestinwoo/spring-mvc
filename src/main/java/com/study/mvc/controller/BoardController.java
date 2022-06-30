package com.study.mvc.controller;

import com.study.mvc.domain.Member.Member;
import com.study.mvc.domain.Member.SessionMember;
import com.study.mvc.domain.board.Board;
import com.study.mvc.repository.BoardRepository;
import com.study.mvc.repository.MemberRepository;
import com.study.mvc.service.BoardService;
import com.study.mvc.validator.BoardValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionAttributeStore;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class BoardController {
    private final BoardRepository boardRepository;
    private final MemberRepository memberRepository;
    private final BoardValidator boardValidator;
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

    @GetMapping("/board/form/{id}")
    public String form(@PathVariable Long id, Model model, HttpSession session){
        SessionMember sessionMember = (SessionMember) session.getAttribute("member");
        boardRepository.findById(id).ifPresentOrElse(b -> {
            memberRepository.findByName(sessionMember.getName()).ifPresentOrElse(m -> {
                if(!b.getWriter().getId().equals(m.getId())) {
                    throw new IllegalStateException("작성자만 게시글 수정이 가능합니다.");
                }
                model.addAttribute("board", b);
            }, () -> {
                throw new IllegalStateException("로그인 정보가 없습니다.");
            });
        }, () -> {
        throw new IllegalStateException("로그인 정보가 없습니다.");
        });

        return "board/form";
    }

    @PostMapping("/board/form")
    public String writeBoard(@Valid Board board, BindingResult bindingResult) {
        boardValidator.validate(board, bindingResult);
        if(bindingResult.hasErrors()) {
            return "board/form";
        }
        boardRepository.save(board);
        return "redirect:/";
    }

    //게시글 상세
    @GetMapping("/board/{id}")
    public String boardDetail(Model model, HttpSession session, @PathVariable Long id) {
        SessionMember member = (SessionMember) session.getAttribute("member");
        Board board = boardRepository.findById(id).orElse(null);
        if(member != null && member.getId().equals(board.getWriter().getId())) {
            model.addAttribute("postOwner", true);
        }
        model.addAttribute("board", board);
        return "board/detail";
    }
}
