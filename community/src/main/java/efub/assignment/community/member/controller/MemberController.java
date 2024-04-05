package efub.assignment.community.member.controller;

import efub.assignment.community.member.domain.Member;
import efub.assignment.community.member.dto.MemberResponseDto;
import efub.assignment.community.member.dto.SignUpRequestDto;
import efub.assignment.community.member.service.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/members")
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

    // 회원가입 기능
    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    public MemberResponseDto signUp(@RequestBody @Valid final SignUpRequestDto requestDto) {
        Long id = memberService.signUp(requestDto);
        Member findMember = memberService.findMemberById(id);
        return MemberResponseDto.from(findMember);
    }


}
