package efub.assignment.community.member.domain;

import efub.assignment.community.global.entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Member extends BaseTimeEntity {
    //memberId
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id", updatable = false)
    private Long memberId;

    //email
    @Column(nullable = false, length = 60)
    private String email;

    // password
    @Column (nullable = false)
    private  String encodePassword;

    // nickname
    @Column(nullable = false, updatable = true, length = 16)
    private String nickname;

    // 학번
    @Column(nullable = false, updatable = false, length = 16)
    private String studentId;

    // 대학교
    @Column(nullable = false, updatable = false, length = 50)
    private String university;

    @Enumerated(EnumType.STRING)  // 등록상태와 해지로 인식
    private MemberStatus status;


    @Builder // 객체 생성
    public Member(String email, String password, String nickname, String studentId, String university) {
        this.email = email;
        this.encodePassword = password;
        this.nickname = nickname;
        this.studentId = studentId;
        this.university = university;
    }

    public void updateMember(String nickname) {
        this.nickname = nickname;
    }

    public void withdrawMember() {
        this.status = MemberStatus.UNREGISTERED;
    }
}