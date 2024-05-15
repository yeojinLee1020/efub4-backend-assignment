package efub.assignment.community.comment.repository;

import efub.assignment.community.comment.domain.Comment;
import efub.assignment.community.comment.domain.CommentHeart;
import efub.assignment.community.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CommentHeartRepository extends JpaRepository<CommentHeart, Long> {
    Integer countByComment(Comment post);

    List<CommentHeart> findByWriter(Member member);

    boolean existsByWriterAndComment(Member member, Comment post);

    Optional<CommentHeart> findByWriterAndComment(Member member, Comment post);
}
