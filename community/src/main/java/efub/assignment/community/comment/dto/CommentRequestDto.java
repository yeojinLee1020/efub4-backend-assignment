package efub.assignment.community.comment.dto;

import efub.assignment.community.comment.domain.Comment;
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
public class CommentRequestDto {

    @NotBlank(message = "게시글 id는 필수입니다.")
    private String postId;

    @NotBlank(message = "작성자 id는 필수입니다.")
    private String memberId;

    private Boolean anonymity;

    @NotBlank(message = "댓글 내용은 필수입니다.")
    private String content;

    public Comment toEntity(Post post, Member member){
        return Comment.builder()
                .post(post)
                .member(member)
                .anonymity(anonymity)
                .content(content)
                .build();
    }
}
