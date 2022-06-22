package com.study.mvc.controller;

import com.study.mvc.domain.board.Board;
import com.study.mvc.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class BoardController {
    private final BoardService boardService;
    @GetMapping("/")
    public String list(Model model) {
        List<Board> boards = boardService.getBoardList();
        model.addAttribute("boards", boards);
        return "/index";
    }
}
