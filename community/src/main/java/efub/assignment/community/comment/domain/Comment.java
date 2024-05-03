package efub.assignment.community.comment.domain;

import efub.assignment.community.global.entity.BaseTimeEntity;
import efub.assignment.community.member.domain.Member;
import efub.assignment.community.post.domain.Post;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Comment extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id", updatable = false)
    private Long commentId;

    @ManyToOne
    @JoinColumn(name = "post_id", updatable = false, nullable = false)
    private Post post;

    @ManyToOne
    @JoinColumn(name = "member_id", updatable = false, nullable = false)
    private Member member;

    @Column(name = "anonymity", updatable = false, nullable = false)
    private Boolean anonymity;

    @Column(name = "content", updatable = true, nullable = false, length = 500)
    private String content;

    @Builder
    public Comment(Post post, Member member, Boolean anonymity, String content) {
        this.post = post;
        this.member = member;
        this.anonymity = anonymity;
        this.content = content;
    }
}

