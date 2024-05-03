package efub.assignment.community.board.service;

import efub.assignment.community.board.domain.Board;
import efub.assignment.community.board.dto.BoardRequestDto;
import efub.assignment.community.board.dto.BoardUpdateRequestDto;
import efub.assignment.community.board.repository.BoardRepository;
import efub.assignment.community.member.domain.Member;
import efub.assignment.community.member.service.MemberService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;
    private final MemberService memberService;

    public Long createBoard(BoardRequestDto dto) {
        Member member = memberService.findMemberById(Long.parseLong(dto.getMemberId()));
        Board board = dto.toEntity(member);
        Board savedBoard = boardRepository.save(board);
        return savedBoard.getBoardId();
    }

    @Transactional(readOnly = true)
    public Board findBoardById(Long boardId){
        Board board = boardRepository.findById(boardId)
                .orElseThrow(()-> new EntityNotFoundException("해당 Id를 가진 Board를 찾을 수 없습니다. id=" +boardId));
        return board;
    }

    public Long update(Long boardId, BoardUpdateRequestDto requestDto){
        Board board = boardRepository.findById(boardId)
                .orElseThrow(()-> new EntityNotFoundException("해당 id를 가진 board를 찾을 수 없습니다."));
        Member member = memberService.findMemberById(Long.parseLong(requestDto.getMemberId()));
        board.updateBoard(member);
        return board.getBoardId();
    }

    public void deleteBoard(Long id){
        Board board = boardRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("해당 id를 가진 Board를 찾을 수 없습니다. id="+id));
        boardRepository.delete(board);
    }
}
