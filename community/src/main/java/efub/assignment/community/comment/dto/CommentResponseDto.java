package efub.assignment.community.comment.dto;

import efub.assignment.community.comment.domain.Comment;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class CommentResponseDto {

    private Long commentId;
    private Long postId;
    private Long memberId;
    private String writerNickname;
    private Boolean anonymity;
    private String content;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;

    public static CommentResponseDto from(Comment comment){
        return new CommentResponseDto(
                comment.getCommentId(),
                comment.getPost().getPostId(),
                comment.getMember().getMemberId(),
                comment.getMember().getNickname(),
                comment.getAnonymity(),
                comment.getContent(),
                comment.getCreatedDate(),
                comment.getModifiedDate()
        );
    }
}
