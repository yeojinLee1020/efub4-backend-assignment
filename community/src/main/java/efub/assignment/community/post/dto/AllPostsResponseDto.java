package efub.assignment.community.post.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class AllPostsResponseDto {
    private List<PostResponseDto> posts;
    private long count;

}
