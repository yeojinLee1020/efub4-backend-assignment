package efub.assignment.community.board.service;

import efub.assignment.community.board.domain.Board;
import efub.assignment.community.board.dto.BoardRequestDto;
import efub.assignment.community.board.dto.BoardUpdateRequestDto;
import efub.assignment.community.board.repository.BoardRepository;
import efub.assignment.community.exception.CustomDeleteException;
import efub.assignment.community.exception.ErrorCode;
import efub.assignment.community.member.domain.Member;
import efub.assignment.community.member.service.MemberService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static efub.assignment.community.exception.ErrorCode.DELETE_PERMISSION_REJECTED_USER;
import static efub.assignment.community.exception.ErrorCode.UPDATE_PERMISSION_REJECTED_USER;

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
        Long memberId = Long.parseLong(requestDto.getMemberId());
        Member member = memberService.findMemberById(memberId);
        board.updateBoard(member);
        return board.getBoardId();
    }

    public void deleteBoard(Long id, Long memberId){
        Board board = boardRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("해당 id를 가진 Board를 찾을 수 없습니다. id="+id));
        if(!memberId.equals(board.getMember().getMemberId())){ // 게시판 주인이 아닌 경우 삭제 권한 없음 처리
            throw new CustomDeleteException(DELETE_PERMISSION_REJECTED_USER);
        }
        boardRepository.delete(board);
    }
}
