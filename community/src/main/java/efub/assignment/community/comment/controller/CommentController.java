package efub.assignment.community.comment.controller;

import efub.assignment.community.comment.domain.Comment;
import efub.assignment.community.comment.dto.*;
import efub.assignment.community.comment.service.CommentHeartService;
import efub.assignment.community.comment.service.CommentService;
import efub.assignment.community.notification.service.NotificationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/comments")
public class CommentController {
    private final CommentService commentService;
    private final CommentHeartService commentHeartService;
    private final NotificationService notificationService;

    /*댓글 생성*/
    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    public CommentResponseDto createComment(@RequestBody @Valid final CommentRequestDto requestDto){
        Comment savedComment = commentService.createNewComment(requestDto);
        //알림생성
        notificationService.createCommentNotice(savedComment);

        return CommentResponseDto.from(savedComment);
    }

    /*게시글별 댓글 전체 조회*/
    @GetMapping("/{postId}")
    public AllCommentsResponseDto getAllCommentsInPost(@PathVariable(name="postId") Long id){
        List<CommentResponseDto> list = new ArrayList<>();
        List<Comment> comments = commentService.findAllComments();
        for (Comment comment: comments) {
            if(id.equals(comment.getPost().getPostId())){
                CommentResponseDto dto = CommentResponseDto.from(comment);
                list.add(dto);
            }
        }

        long count = commentService.countAllComments();

        return new AllCommentsResponseDto(list, count);
    }

    /*작성자별 댓글 조회*/
    @GetMapping()
    public AllCommentsResponseDto getAllCommentsByMember(@RequestParam(name="memberId") Long id){
        List<CommentResponseDto> list = new ArrayList<>();
        List<Comment> comments = commentService.findAllCommentsByMember(id);
        Long count = 0L;
        for (Comment comment: comments) {
            CommentResponseDto dto = CommentResponseDto.from(comment);
            list.add(dto);
            count += 1;
        }
        return new AllCommentsResponseDto(list, count);
    }

    /*댓글 수정*/
    @PatchMapping("/{commentId}")
    public CommentResponseDto updateComment(@PathVariable(name = "commentId") Long id,
                                      @RequestParam(name = "memberId") Long memberId,
                                      @RequestBody @Valid final CommentUpdateRequestDto requestDto){
        Long commentId = commentService.updateComment(id, memberId, requestDto);
        Comment comment = commentService.findCommentById(commentId);
        return CommentResponseDto.from(comment);
    }


    /*댓글 삭제*/
    @DeleteMapping("/{commentId}")
    public String deleteComment(@PathVariable(name = "commentId") Long id,
                             @RequestParam(name = "memberId") Long memberId){
        commentService.deleteComment(id, memberId);
        return "성공적으로 삭제되었습니다.";
    }

    /* 좋아요 생성 */
    @PostMapping("/{commentId}/hearts")
    @ResponseStatus(value = HttpStatus.CREATED)
    public String createCommentHeart(@PathVariable final Long commentId, @RequestBody final MemberInfoRequestDto requestDto) {
        commentHeartService.create(commentId, requestDto);
        return "좋아요를 눌렀습니다";
    }

    /* 좋아요 삭제 */
    @DeleteMapping("/{commentId}/hearts")
    @ResponseStatus(value = HttpStatus.OK)
    public String deleteCommentHeart(@PathVariable final Long commentId, @RequestParam final Long memberId) {
        commentHeartService.delete(commentId,memberId);
        return "좋아요를 취소했습니다";
    }
}
