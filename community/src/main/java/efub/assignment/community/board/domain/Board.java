package efub.assignment.community.board.domain;

import efub.assignment.community.global.entity.BaseTimeEntity;
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

    @Column(nullable = false, length = 25)
    private String hostNickName;

    @Column(nullable = false, length = 25)
    private String boardName;

    @Column(nullable = false, length = 1000)
    private String explanation;

    @Column(nullable = false, length = 1000)
    private String notice;

    @Builder
    public Board(String hostNickName, String boardName, String explanation, String notice) {
        this.hostNickName = hostNickName;
        this.boardName = boardName;
        this.explanation = explanation;
        this.notice = notice;
    }


    public void updateBoard(String hostNickName){
        this.hostNickName = hostNickName;
    }
}
