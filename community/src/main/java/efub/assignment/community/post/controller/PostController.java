package efub.assignment.community.post.controller;

import efub.assignment.community.post.domain.Post;
import efub.assignment.community.post.dto.AllPostsResponseDto;
import efub.assignment.community.post.dto.PostRequestDto;
import efub.assignment.community.post.dto.PostResponseDto;
import efub.assignment.community.post.dto.PostUpdateRequestDto;
import efub.assignment.community.post.service.PostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/posts")
public class PostController {
    private final PostService postService;

    /* 게시글 생성 */
    @PostMapping()
    @ResponseStatus(value = HttpStatus.CREATED)
    public PostResponseDto createPost(@RequestBody @Valid final PostRequestDto requestDto){
        Post savedPost = postService.createNewPost(requestDto);
        return PostResponseDto.from(savedPost, savedPost.getMember().getNickname());
    }

    /* 게시글 전체 조회 */
    @GetMapping()
    public AllPostsResponseDto getAllPost(){
        List<PostResponseDto> list = new ArrayList<>();
        List<Post> posts = postService.findAllPosts();
        for (Post post : posts) {
            PostResponseDto dto = PostResponseDto.from(post, post.getMember().getNickname());
            list.add(dto);
        }
        long count = postService.countAllPosts();

        return new AllPostsResponseDto(list, count);
    }

    /* 게시글 1개 조회 */
    @GetMapping("/{postId}")
    public PostResponseDto getOnePost(@PathVariable(name="postId") Long id){
        Post post = postService.findPostById(id);
        return PostResponseDto.from(post, post.getMember().getNickname());
    }

    /* 게시글 수정 */
    @PatchMapping("/{postId}")
    public PostResponseDto updatePost(@PathVariable(name = "postId") Long id,
                                           @RequestBody @Valid final PostUpdateRequestDto requestDto){
        Long postId = postService.updatePost(id, requestDto);
        Post post = postService.findPostById(postId);
        return PostResponseDto.from(post, post.getMember().getNickname());
    }

    /* 게시글 삭제 */
    @DeleteMapping("/{postId}")
    public String deletePost(@PathVariable(name = "postId") Long id){
        postService.deletePost(id);
        return "성공적으로 삭제되었습니다.";
    }
}
