package efub.assignment.community.post.repository;

import efub.assignment.community.board.domain.Board;
import efub.assignment.community.member.domain.Member;
import efub.assignment.community.post.domain.Post;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;


@DataJpaTest // @WebMvcTest와 @MockBean 같이 사용 불가
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE, connection = EmbeddedDatabaseConnection.H2)
@ExtendWith(SpringExtension.class)
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)  // 언더스코어를 공백으로 대체
class PostRepositoryTest {
    public Member member;
    public Board board;
    public Post post;
    @Autowired
    private PostRepository postRepository;

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
        // Post 객체 초기화
        post = Post.builder()
                .board(board)
                .member(member)
                .anonymity(true)
                .title("게시글 제목")
                .content("게시글 내용")
                .build();
    }

    // 성공하는 경우
    @Test
    public void 게시물_총개수_조회_테스트() {
        //given
        Post post2 = Post.builder()
                .board(board)
                .member(member)
                .anonymity(true)
                .title("게시글 제목")
                .content("게시글 내용")
                .build();
        //when
        int count = (int) postRepository.count();
        //then
        assertThat(count).isEqualTo(2);
    }

    // 실패하는 경우 - 존재하지 않는 게시물 조회시
    @Test
    public void 존재하지_않는_게시물_조회_테스트() {
        // when
        Post foundPost = postRepository.findById(10L).orElse(null); // 존재하지 않는 ID

        // then
        assertThat(foundPost).isNotNull();
    }


}