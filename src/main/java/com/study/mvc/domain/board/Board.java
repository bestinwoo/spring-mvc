package com.study.mvc.domain.board;

import com.study.mvc.domain.Member.Member;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Board {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    //@NotNull
    @Size(min=2, max= 60, message = "크기가 2에서 60 사이여야 합니다.")
    private String title;
    private String content;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "writer_id")
    private Member writer;
    private LocalDate writeDate;

    @PrePersist
    public void prePersist() {
        this.writeDate = this.writeDate == null ? LocalDate.now() : this.writeDate;
    }
}
