package efub.assignment.community.post.service;

import efub.assignment.community.board.domain.Board;
import efub.assignment.community.board.service.BoardService;
import efub.assignment.community.exception.CustomDeleteException;
import efub.assignment.community.exception.ErrorCode;
import efub.assignment.community.member.domain.Member;
import efub.assignment.community.member.service.MemberService;
import efub.assignment.community.post.domain.Post;
import efub.assignment.community.post.dto.PostRequestDto;
import efub.assignment.community.post.dto.PostUpdateRequestDto;
import efub.assignment.community.post.repository.PostRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final MemberService memberService;
    private final BoardService boardService;

    public Post createNewPost(PostRequestDto requestDto){
        Member member = memberService.findMemberById(Long.parseLong(requestDto.getMemberId()));
        Board board = boardService.findBoardById(Long.parseLong(requestDto.getBoardId()));
        Post post = requestDto.toEntity(board, member);
        return postRepository.save(post);

    }

    @Transactional(readOnly = true)
    public List<Post> findAllPosts() {
        return postRepository.findAll();
    }

    @Transactional(readOnly = true)
    public long countAllPosts() {
        return postRepository.count();
    }

    @Transactional(readOnly = true)
    public Post findPostById(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(()->new EntityNotFoundException("해당 id를 가진 post를 찾을 수 없습니다. id=" +id));
        return post;
    }

    @Transactional(readOnly = true)
    public Long updatePost(Long id, Long memberId, PostUpdateRequestDto requestDto) {
        Post post = postRepository.findById(id)
                .orElseThrow(()-> new EntityNotFoundException("해당 id를 가진 post를 찾을 수 없습니다."));
        if(!memberId.equals(post.getMember().getMemberId())){
            throw new CustomDeleteException(ErrorCode.UPDATE_PERMISSION_REJECTED_USER);
        }
        post.update(requestDto);
        return post.getPostId();
    }

    public void deletePost(Long postId, Long memberId) {
        Post post = findPostById(postId);
        if(!memberId.equals(post.getMember().getMemberId())){
            throw new CustomDeleteException(ErrorCode.DELETE_PERMISSION_REJECTED_USER);
        }
        postRepository.delete(post);
    }
}
