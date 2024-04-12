package efub.assignment.community.post.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostUpdateRequestDto {

    @NotBlank(message = "게시글의 내용을 입력해주세요.")
    private String content;

    @Builder
    public PostUpdateRequestDto(String content) {
        this.content = content;
    }
}
