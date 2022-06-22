package com.study.mvc.service;

import com.study.mvc.domain.board.Board;
import com.study.mvc.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;

    public List<Board> getBoardList() {
        return boardRepository.findAll();
    }
}
