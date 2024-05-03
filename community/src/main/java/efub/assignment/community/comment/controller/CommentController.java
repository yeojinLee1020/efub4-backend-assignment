package efub.assignment.community.comment.controller;

import efub.assignment.community.comment.domain.Comment;
import efub.assignment.community.comment.dto.CommentRequestDto;
import efub.assignment.community.comment.dto.CommentResponseDto;
import efub.assignment.community.comment.service.CommentService;
import efub.assignment.community.post.dto.PostRequestDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/comments")
public class CommentController {
    private final CommentService commentService;

    /*댓글 생성*/
    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    public CommentResponseDto createComment(@RequestBody @Valid final CommentRequestDto requestDto){
        Comment savedComment = commentService.createNewComment(requestDto);
        return CommentResponseDto.from(savedComment);
    }

    /*댓글 전체 조회*/
    /*댓글 상세 조회*/
    /*댓글 수정*/
    /*댓글 삭제*/

}
