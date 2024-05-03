package efub.assignment.community.board.controller;

import efub.assignment.community.board.domain.Board;
import efub.assignment.community.board.dto.BoardRequestDto;
import efub.assignment.community.board.dto.BoardResponseDto;
import efub.assignment.community.board.dto.BoardUpdateRequestDto;
import efub.assignment.community.board.service.BoardService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("/board")
public class BoardController {
    private final BoardService boardService;

    /* 게시판 생성 */
    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    public BoardResponseDto createBoard(@RequestBody @Valid final BoardRequestDto dto){
        Long id = boardService.createBoard(dto);
        Board savedboard = boardService.findBoardById(id);
        return BoardResponseDto.from(savedboard);
    }

    /* 게시글 조회 */
    @GetMapping("/{boardId}")
    public BoardResponseDto getBoard(@PathVariable Long boardId){
        Board findBoard = boardService.findBoardById(boardId);
        return BoardResponseDto.from(findBoard);
    }

    /* 게시판 수정 */
    @PatchMapping("/{boardId}")
    public BoardResponseDto update(@PathVariable final Long boardId, @RequestBody @Valid final BoardUpdateRequestDto requestDto){
        Long id = boardService.update(boardId, requestDto);
        Board findBoard = boardService.findBoardById(id);
        return BoardResponseDto.from(findBoard);

    }

    /* 게시판 삭제 */
    @DeleteMapping("/{boardId}")
    public String deleteBoard(@PathVariable(name = "boardId")Long id,
                              @RequestParam(name = "memberId") Long memberId){ // requestparameter로 삭제하려는 사용자 id 받아옴 (권한 확인 위함)
        boardService.deleteBoard(id, memberId);
        return "성공적으로 삭제되었습니다";
    }
}
