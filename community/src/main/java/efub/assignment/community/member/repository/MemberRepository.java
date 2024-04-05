package efub.assignment.community.member.repository;

import efub.assignment.community.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Boolean existsByEmail(String mail);
}
