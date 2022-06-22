package com.study.mvc.domain.board;

import com.study.mvc.domain.Member.Member;
import lombok.*;

import javax.persistence.*;
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
