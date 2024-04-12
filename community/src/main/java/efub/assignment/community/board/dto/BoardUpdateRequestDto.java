package efub.assignment.community.board.dto;


import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BoardUpdateRequestDto {

    @NotBlank(message = "변경할 닉네임을 입력해주세요")
    private String hostNickName;

    @Builder
    public BoardUpdateRequestDto(String hostNickName){
        this.hostNickName = hostNickName;
    }
}
