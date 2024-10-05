package efub.assignment.community.post.domain;

import efub.assignment.community.board.domain.Board;
import efub.assignment.community.member.domain.Member;
import efub.assignment.community.post.dto.PostUpdateRequestDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;


@ExtendWith(SpringExtension.class)
@MockBean(JpaMetamodelMappingContext.class)
@WebMvcTest(Post.class)
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)  // 언더스코어를 공백으로 대체
class PostTest {
    public Member member;
    public Board board;
    public Post post;

    @BeforeEach
    public void setUp() {
        //member 객체 초기화
        member = Member.builder()
                .email("fubby@gmail.com")
                .password("abcd1234!")
                .nickname("fubby")
                .studentId("2101001")
                .university("OOOO University")
                .build();
        // board 객체 초기화
        board = Board.builder()
                .member(member)
                .boardName("게시판 제목")
                .explanation("게시판 설명")
                .notice("게시판 공지")
                .build();

    }

    // 성공하는 경우1
    @Test
    public void 게시글_생성_테스트() {
        //given
        post = Post.builder()
                .board(board)
                .member(member)
                .anonymity(true)
                .title("게시글 제목")
                .content("게시글 내용")
                .build();
        //when
        //then
        assertThat(post).isNotNull();
        assertThat(post.getTitle()).isEqualTo("게시글 제목");
        assertThat(post.getContent()).isEqualTo("게시글 내용");
    }

    // 성공하는 경우2
    @Test
    public void 게시물_업데이트_성공_테스트() {
        // given
        post = Post.builder()
                .board(board)
                .member(member)
                .anonymity(true)
                .title("게시글 제목")
                .content("게시글 내용")
                .build();
        PostUpdateRequestDto requestDto = new PostUpdateRequestDto("Updated Content");

        // when
        post.update(requestDto);

        // then
        assertThat(post.getContent()).isEqualTo(requestDto.getContent());
    }

    // 실패하는 경우
    @Test
    public void 특수문자가_포함된_제목의_게시글_생성불가_테스트() {
        // given
        post = Post.builder()
                .board(board)
                .member(member)
                .anonymity(true)
                .title("게시글 제목!@#$")
                .content("게시글 내용")
                .build();

        // when
        // then
        // when & then
        assertThrows(IllegalArgumentException.class, () -> {
            Post post = Post.builder()
                    .board(board)
                    .member(member)
                    .anonymity(true)
                    .title("게시글 제목!@#$")
                    .content("게시글 내용")
                    .build();
        });
    }
}