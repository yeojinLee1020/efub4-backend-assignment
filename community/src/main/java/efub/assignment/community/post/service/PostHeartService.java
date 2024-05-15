package efub.assignment.community.post.service;

import efub.assignment.community.member.domain.Member;
import efub.assignment.community.member.service.MemberService;
import efub.assignment.community.post.domain.Post;
import efub.assignment.community.post.domain.PostHeart;
import efub.assignment.community.post.dto.HeartRequestDto;
import efub.assignment.community.post.repository.PostHeartRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class PostHeartService {
    
    private final PostHeartRepository postHeartRepository;
    private final PostService postService;
    private final MemberService memberService;

    // 좋아요 등록
    public void create(Long postId, HeartRequestDto requestDto) {
        Member member = memberService.findMemberById(requestDto.getMemberId());
        Post post = postService.findPostById(postId);

        if (isExistsByWriterAndPost(member, post)) {
            throw new RuntimeException("이미 좋아요를 눌렀습니다.");
        }
        PostHeart postHeart = PostHeart.builder()
                .post(post)
                .member(member)
                .build();
        postHeartRepository.save(postHeart);
    }
    
    // 좋아요 삭제
    public void delete(Long postId, Long memberId) {
        Post post = postService.findPostById(postId);
        Member member = memberService.findMemberById(memberId);
        PostHeart postLike = postHeartRepository.findByWriterAndPost(member, post)
                .orElseThrow(() -> new RuntimeException(""));
        postHeartRepository.delete(postLike);
    }
    
    // 좋아요 상태 확인
    public boolean isHeart(Long memberId, Post post){
        Member member = memberService.findMemberById(memberId);
        return isExistsByWriterAndPost(member, post);
    }

    @Transactional(readOnly = true)
    public boolean isExistsByWriterAndPost(Member member, Post post) {
        return postHeartRepository.existsByWriterAndPost(member, post);
    }

    @Transactional(readOnly = true)
    public Integer countPostHeart(Post post) {
        Integer count = postHeartRepository.countByPost(post);
        return count;
    }
    
    
}
