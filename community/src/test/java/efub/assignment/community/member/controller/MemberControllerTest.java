package efub.assignment.community.member.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import efub.assignment.community.CommunityApplication;
import efub.assignment.community.member.dto.SignUpRequestDto;
import efub.assignment.community.member.repository.MemberRepository;
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
public class MemberControllerTest {

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected WebApplicationContext context;

    @Autowired
    protected ObjectMapper objectMapper; // 직렬화, 역직렬화를 위한 클래스

    @Autowired
    protected MemberRepository memberRepository;


    @BeforeEach // 테스트 실행 전 실행하는 메서드
    public void mockMvcSetUp(){
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context)
                .build();
    }

    /* 회원가입 테스트 - 성공  */
    @Test
    @DisplayName("createMember : 회원가입 성공")
    public void createMemberTest() throws Exception{
        // given
        final String url = "/members";
        final String email = "newone2@ewhain.net";
        final String password = "!efub1234!";
        final String nickname = "efubBack";
        final String studentId = "2112123";
        final String university = "OO college";
        final SignUpRequestDto requestDto = createDefaultSignUpRequestDto(email, password, nickname, studentId, university);

        // when
        final String requestBody = objectMapper.writeValueAsString(requestDto);

        ResultActions resultActions = mockMvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody));

        // then
        resultActions
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.accountId").isNotEmpty())
                .andExpect(jsonPath("$.email").value(email))
                .andExpect(jsonPath("$.nickname").value(nickname))
                .andExpect(jsonPath("$.studentId").value(studentId))
                .andExpect(jsonPath("$.university").value(university));
    }

    private SignUpRequestDto createDefaultSignUpRequestDto(String email,String password, String nickname, String studentId, String university){
        return SignUpRequestDto.builder()
                .email(email)
                .password(password)
                .nickname(nickname)
                .studentId(studentId)
                .university(university)
                .build();
    }


    /* 회원조회 테스트 - 실패 */
    @Test
    @DisplayName("getMember : 회원 조회 ")
    public void getMemberTest() throws Exception {
        // given
        final String url = "/members/{memberId}"; // 회원 조회 URL
        final Long nonExistentId = 100L; // 존재하지 않는 ID

        // when
        ResultActions resultActions = mockMvc.perform(get(url, nonExistentId)
                .contentType(MediaType.APPLICATION_JSON));

        // then
        resultActions
                .andExpect(status().isFound());
    }

}