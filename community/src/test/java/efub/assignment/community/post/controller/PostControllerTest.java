package efub.assignment.community.post.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import efub.assignment.community.CommunityApplication;
import efub.assignment.community.board.domain.Board;
import efub.assignment.community.board.repository.BoardRepository;
import efub.assignment.community.member.domain.Member;
import efub.assignment.community.member.repository.MemberRepository;
import efub.assignment.community.post.domain.Post;
import efub.assignment.community.post.dto.PostRequestDto;
import efub.assignment.community.post.repository.PostRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.junit.jupiter.api.Assertions.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
@Sql(scripts =  "/data.sql")
@ActiveProfiles("test")
@ContextConfiguration(classes = CommunityApplication.class)
@TestPropertySource(locations = "classpath:application-test.yml")
class PostControllerTest {

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected WebApplicationContext context;

    @Autowired
    protected ObjectMapper objectMapper; // 직렬화, 역직렬화를 위한 클래스

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private BoardRepository boardRepository;
    @Autowired
    protected PostRepository postRepository;

    public Member member;
    public Board board;
    public Post post;

    @BeforeEach // 테스트 실행 전 실행하는 메서드
    public void mockMvcSetUp(){
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context)
                .build();
    }

    /* 게시글 생성 테스트 - 성공 */
    @Test
    @DisplayName("createPost : 게시글 생성 성공")
    void createPost() throws Exception {
        //given
        //member 객체 초기화
        member = Member.builder()
                .email("fubby@gmail.com")
                .password("abcd1234!")
                .nickname("fubby")
                .studentId("2101001")
                .university("OOOO University")
                .build();
        memberRepository.save(member); // Member 저장
        // board 객체 초기화
        board = Board.builder()
                .member(member)
                .boardName("게시판 제목")
                .explanation("게시판 설명")
                .notice("게시판 공지")
                .build();
        boardRepository.save(board); // Board 저장

        final String url = "/posts";
        final String boardId = String.valueOf(board.getBoardId());; // 게시판 ID
        final String memberId = String.valueOf(member.getMemberId()); // 계정 ID
        final boolean anonymity = false;
        final String title = "Test Title";
        final String content = "Test Content";
        final PostRequestDto requestDto = new PostRequestDto(boardId, memberId, anonymity, title, content);

        System.out.println(url);
        System.out.println(boardId);
        System.out.println(memberId);
        System.out.println(anonymity);
        System.out.println(title);
        System.out.println(content);

        //when
        final String requestBody = objectMapper.writeValueAsString(requestDto);

        ResultActions resultActions = mockMvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody));

        //then
        resultActions
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.boardId").value(boardId))
                .andExpect(jsonPath("$.anonymity").value(anonymity))
                .andExpect(jsonPath("$.title").value(title))
                .andExpect(jsonPath("$.content").value(content));
    }

    /* 게시글 조회 테스트 - 실패 */
    @Test
    void getOnePost() throws Exception{
        //given
        //member 객체 초기화
        member = Member.builder()
                .email("fubby@gmail.com")
                .password("abcd1234!")
                .nickname("fubby")
                .studentId("2101001")
                .university("OOOO University")
                .build();
        memberRepository.save(member); // Member 저장
        // board 객체 초기화
        board = Board.builder()
                .member(member)
                .boardName("게시판 제목")
                .explanation("게시판 설명")
                .notice("게시판 공지")
                .build();
        boardRepository.save(board); // Board 저장
        // post 객체 초기화
        post = Post.builder()
                .board(board)
                .member(member)
                .anonymity(true)
                .title("게시글 제목")
                .content("게시글 내용")
                .build();
        postRepository.save(post); // post 저장

        final String url = "/posts/{postId}";
        final Long nonExistentPostId = 1000L;

        //when
        ResultActions resultActions = mockMvc.perform(get(url, nonExistentPostId)
                .contentType(MediaType.APPLICATION_JSON));

        // then
        resultActions
                .andExpect(status().isFound());

    }
}