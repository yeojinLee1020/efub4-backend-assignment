package efub.assignment.community.member.dto;

import efub.assignment.community.member.domain.Member;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SignUpRequestDto {

    @NotBlank(message = "이메일은 필수입니다.")  // 해당 값이 null이 아니고, 공백(""과 " " 모두 포함)이 아닌지 검증
    @Email(message = "유효하지 않은 이메일 형식입니다.",
            regexp = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$")
    private String email;

    @NotBlank(message = "비밀번호는 필수입니다.") // 해당 값이 null이 아니고, 공백(""과 " " 모두 포함)이 아닌지 검증
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[!.?,])[A-Za-z\\d!.?,]{2,16}$",
            message = "16자 이내의 영문자 및 숫자와 ?,!,., , 특수문자로 입력해주세요.")
    private String password;

    @NotBlank(message = "닉네임은 필수입니다. ") // 해당 값이 null이 아니고, 공백(""과 " " 모두 포함)이 아닌지 검증
    private String nickname;

    @NotBlank(message = "학번은 필수입니다.")
    private String studentId;

    @NotBlank(message = "대학교는 필수입니다.")
    private String university;

    public SignUpRequestDto(String email, String password, String nickname, String studentId, String university) {
        this.email = email;
        this.password = password;
        this.nickname = nickname;
        this.studentId = studentId;
        this.university = university;
    }

    @Builder
    public Member toEntity() { //dto를 account 엔티티 객체로 변환
        return Member.builder()
                .email(this.email)
                .password(this.password)
                .nickname(this.nickname)
                .studentId(this.studentId)
                .university(this.university)
                .build();
    }
}
