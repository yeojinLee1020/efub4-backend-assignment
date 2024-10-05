package efub.assignment.community.member.domain;

import efub.assignment.community.post.domain.Post;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@MockBean(JpaMetamodelMappingContext.class) // 필요!
@WebMvcTest(Member.class)
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)  // 언더스코어를 공백으로 대체
class MemberTest {

    // 성공하는 경우1
    @Test
    public void 멤버_객체_생성_테스트() {
        // given
        String email = "fubby@gmail.com";
        String password = "abcd1234!";
        String nickname = "fubby";
        String studentId = "2101001";
        String university = "OOOO University";

        // when
        Member member = Member.builder()
                .email(email)
                .password(password)
                .nickname(nickname)
                .studentId(studentId)
                .university(university)
                .build();

        // then
        assertEquals(email, member.getEmail());  // assertEquals(기대값, 실제값)
        assertEquals(password, member.getEncodePassword());
        assertEquals(nickname, member.getNickname());
        assertEquals(studentId, member.getStudentId());
        assertEquals(university, member.getUniversity());
    }


    // 성공하는 경우2
    @Test
    public void 멤버_업데이트_테스트() {
        // given
        String newNickname = "수정된닉네임퍼비";
        Member member = Member.builder()
                .email("fubby@gmail.com")
                .password("abcd1234!")
                .nickname("닉네임퍼비!@#")
                .studentId("2101001")
                .university("OOOO University")
                .build();

        // when
        member.updateMember(newNickname);

        // then
        assertEquals(newNickname, member.getNickname());
    }

    // 성공하는 경우3
    @Test
    public void 멤버_탈퇴_테스트() {
        Member member = Member.builder()
                .email("fubby@gmail.com")
                .password("abcd1234!")
                .nickname("닉네임퍼비")
                .studentId("2101001")
                .university("OOOO University")
                .build();
        //when
        member.withdrawMember();

        //then
        assertEquals(MemberStatus.UNREGISTERED, member.getStatus());
    }

    // 실패하는 경우
    @Test
    public void 특수문자_포함된_닉네임_등록불가_테스트() {
        // given
        // when
        // then
        assertThrows(IllegalArgumentException.class, () -> {
            Member member = Member.builder()
                    .email("fubby@gmail.com")
                    .password("abcd1234!")
                    .nickname("닉네임퍼비!@#")
                    .studentId("2101001")
                    .university("OOOO University")
                    .build();
        });
    }

}