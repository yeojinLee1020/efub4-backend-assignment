package efub.assignment.community.post.domain;

import efub.assignment.community.board.domain.Board;
import efub.assignment.community.global.entity.BaseTimeEntity;
import efub.assignment.community.member.domain.Member;
import efub.assignment.community.post.dto.PostUpdateRequestDto;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Post extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id", updatable = false)
    private Long postId;

    @ManyToOne
    @JoinColumn(name = "board_id", updatable = false)
    private Board board;

    @ManyToOne
    @JoinColumn(name = "member_id", updatable = false)
    private Member member;

    @Column(nullable = false)
    private Boolean anonymity;

    @Column(nullable = false, length = 50)
    private String title;

    @Column(nullable = false, length = 1000)
    private String content;


    @Builder
    public Post(Board board, Member member, Boolean anonymity, String title, String content) {
        this.board = board;
        this.member = member;
        this.anonymity = anonymity;
        this.title = title;
        this.content = content;
    }

    public void update(PostUpdateRequestDto requestDto) {
        this.content = requestDto.getContent();
    }

}
