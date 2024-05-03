package efub.assignment.community.post.dto;

import efub.assignment.community.board.domain.Board;
import efub.assignment.community.member.domain.Member;
import efub.assignment.community.post.domain.Post;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class PostRequestDto {

    @NotBlank(message = "게시판 id는 필수입니다.")
    private String boardId;

    @NotBlank(message = "계정 id는 필수입니다.")
    private String memberId;

//    @NotBlank 사용 불가 -> 문자열일 경우만 사용가능한 어노테이션
    private boolean anonymity;

    @NotBlank(message = "제목은 필수입니다.")
    private String title;

    @NotBlank(message = "내용은 필수입니다.")
    private String content;

    public Post toEntity(Board board, Member member){
        return Post.builder()
                .board(board)
                .member(member)
                .anonymity(anonymity)
                .title(this.title)
                .content(this.content)
                .build();
    }
}
