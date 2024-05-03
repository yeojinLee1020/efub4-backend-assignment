package efub.assignment.community.board.domain;

import efub.assignment.community.global.entity.BaseTimeEntity;
import efub.assignment.community.member.domain.Member;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Board extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //
    @Column(name = "board_id", updatable = false)
    private Long boardId;

    @ManyToOne
    @JoinColumn(name = "member_id", updatable = true) // member 테이블과 매핑을 통해 게시판의 주인 표현
    private Member member;

    @Column(nullable = false, length = 25)
    private String boardName;

    @Column(nullable = false, length = 1000)
    private String explanation;

    @Column(nullable = false, length = 1000)
    private String notice;

    @Builder
    public Board(Member member, String boardName, String explanation, String notice) {
        this.member = member;
        this.boardName = boardName;
        this.explanation = explanation;
        this.notice = notice;
    }

    public void updateBoard(Member member){
        this.member = member;
    }
}
