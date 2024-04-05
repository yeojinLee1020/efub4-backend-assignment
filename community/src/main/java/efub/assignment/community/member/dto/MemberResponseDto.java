package efub.assignment.community.member.dto;

import efub.assignment.community.member.domain.Member;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access= AccessLevel.PROTECTED)
public class MemberResponseDto {
    private Long accountId;
    private String email;
    private String nickname;
    private String studentId;
    private String university;

    public MemberResponseDto(Long accountId, String email, String nickname, String studentId, String university) {
        this.accountId = accountId;
        this.email = email;
        this.nickname = nickname;
        this.studentId = studentId;
        this.university = university;
    }

    public static MemberResponseDto from (Member member) {
        return new MemberResponseDto(member.getMemberId(),
        member.getEmail(),
        member.getNickname(),
        member.getStudentId(),
        member.getUniversity());
    }
}
