package efub.assignment.community.comment.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class AllCommentsResponseDto {
    private List<CommentResponseDto> comments;
    private Long count;
}
