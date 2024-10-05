package efub.assignment.community.member.repository;

import efub.assignment.community.member.domain.Member;
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

@DataJpaTest // @WebMvcTest와 @MockBean 같이 사용 불가
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE, connection = EmbeddedDatabaseConnection.H2)
@ExtendWith(SpringExtension.class)
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)  // 언더스코어를 공백으로 대체
class MemberRepositoryTest {
    @Autowired
    private MemberRepository memberRepository;

    private Member member;

    @BeforeEach
    public void setUp() {
        // 각 테스트 전 멤버 객체 생성 - 테스트마다 독립성 보장
        String email = "fubby@gmail.com";
        String password = "abcd1234!";
        String nickname = "fubby";
        String studentId = "2101001";
        String university = "OOOO University";
        member = Member.builder()
                .email(email)
                .password(password)
                .nickname(nickname)
                .studentId(studentId)
                .university(university)
                .build();
    }

    //성공하는 테스트
    @Test
    public void 멤버_저장_테스트() {
        //given

        //when
        Member savedMember = memberRepository.save(member);

        //then
        assertThat(savedMember).isNotNull();
        assertThat(savedMember.getEmail()).isEqualTo(member.getEmail());
    }

    //성공하는 테스트
    @Test
    public void 이메일로_멤버_유무_조회_테스트() {
        // given
        memberRepository.save(member);

        // when
        Boolean isExists = memberRepository.existsByEmail(member.getEmail());

        // then
        assertThat(isExists).isTrue();
    }

    //실패하는 테스트
    @Test
    public void 이메일로_멤버_유무_조회_테스트_실패하는_경우() {
        // given
        memberRepository.save(member);

        // when
        Boolean isExists = memberRepository.existsByEmail(member.getEmail());

        // then
        assertThat(isExists).isFalse();
    }
}


