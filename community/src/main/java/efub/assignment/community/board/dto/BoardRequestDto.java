package efub.assignment.community.board.dto;

import efub.assignment.community.board.domain.Board;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class BoardRequestDto {
    @NotBlank(message = "게시판 주인 닉네임은 필수입니다.")
    private String hostNickName;

    @NotBlank(message = "게시판 이름은 필수입니다.")
    private String boardName;

    @NotBlank(message = "게시판 설명은 필수입니다.")
    private String explanation;

    private String notice;

    public Board toEntity() {
        return Board.builder()
                .hostNickName(this.hostNickName)
                .boardName(this.boardName)
                .explanation(this.explanation)
                .notice(this.notice)
                .build();
    }
}
