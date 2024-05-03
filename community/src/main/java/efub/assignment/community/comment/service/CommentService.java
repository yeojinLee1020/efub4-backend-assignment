package efub.assignment.community.comment.service;

import efub.assignment.community.comment.domain.Comment;
import efub.assignment.community.comment.dto.CommentRequestDto;
import efub.assignment.community.comment.repository.CommentRepository;
import efub.assignment.community.member.domain.Member;
import efub.assignment.community.member.service.MemberService;
import efub.assignment.community.post.domain.Post;
import efub.assignment.community.post.service.PostService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
}
