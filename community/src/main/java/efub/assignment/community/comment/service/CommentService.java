package efub.assignment.community.comment.service;

import efub.assignment.community.comment.domain.Comment;
import efub.assignment.community.comment.dto.CommentRequestDto;
import efub.assignment.community.comment.dto.CommentUpdateRequestDto;
import efub.assignment.community.comment.repository.CommentRepository;
import efub.assignment.community.exception.CustomDeleteException;
import efub.assignment.community.exception.ErrorCode;
import efub.assignment.community.member.domain.Member;
import efub.assignment.community.member.service.MemberService;
import efub.assignment.community.post.domain.Post;
import efub.assignment.community.post.service.PostService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final MemberService memberService;
    private final PostService postService;

    public Comment createNewComment(CommentRequestDto requestDto){
        Post post = postService.findPostById(Long.parseLong(requestDto.getPostId()));
        Member member = memberService.findMemberById(Long.parseLong(requestDto.getMemberId()));
        Comment comment = requestDto.toEntity(post, member);
        Comment savedComment = commentRepository.save(comment);
        return savedComment;
    }

    @Transactional(readOnly = true)
    public List<Comment> findAllComments() {
        List<Comment> comments = commentRepository.findAll();
        return comments;
    }

    @Transactional(readOnly = true)
    public long countAllComments(){
        return commentRepository.count();
    }

    @Transactional(readOnly = true)
    public List<Comment> findAllCommentsByMember(Long id) {
        List<Comment> commentsByMember = new ArrayList<>();
        List<Comment> comments = commentRepository.findAll();
        for (Comment comment: comments) {
            if(id.equals(comment.getMember().getMemberId())){
                commentsByMember.add(comment);
            }
        }

        return commentsByMember;
    }

    @Transactional(readOnly = false)
    public Long updateComment(Long id, Long memberId, CommentUpdateRequestDto requestDto) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(()-> new EntityNotFoundException("해당 id를 가진 comment를 찾을 수 없습니다."));
        if(!memberId.equals(comment.getMember().getMemberId())){
            throw new CustomDeleteException(ErrorCode.UPDATE_PERMISSION_REJECTED_USER);
        }
        comment.update(requestDto);
        return comment.getCommentId();
    }

    @Transactional(readOnly = true)
    public Comment findCommentById(Long commentId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(()->new EntityNotFoundException("해당 id를 가진 comment를 찾을 수 없습니다. id=" + commentId));
        return comment;
    }

    public void deleteComment(Long commentId, Long memberId) {
        Comment comment = findCommentById(commentId);
        if(!memberId.equals(comment.getMember().getMemberId())){
            throw new CustomDeleteException(ErrorCode.DELETE_PERMISSION_REJECTED_USER);
        }
        commentRepository.delete(comment);
    }

    // 해당 게시글의 모든 댓글 가져오기
    public List<Comment> findAllCommentsByPost(Long id) {
        List<Comment> commentsByPost = new ArrayList<>();
        List<Comment> comments = commentRepository.findAll(); // 모든 댓글 가져오기
        for (Comment comment: comments) {
            if(id.equals(comment.getPost().getPostId())){ // 해당 댓글의 게시글의 주인이 받은 id와 같은 거 골라내기
                commentsByPost.add(comment);
            }
        }




        return commentsByPost;
    }
}
