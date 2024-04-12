package efub.assignment.community.board.service;

import efub.assignment.community.board.domain.Board;
import efub.assignment.community.board.dto.BoardRequestDto;
import efub.assignment.community.board.dto.BoardUpdateRequestDto;
import efub.assignment.community.board.repository.BoardRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;

    public Long createBoard(BoardRequestDto dto) {
        Board board = dto.toEntity();
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
        Board board = findBoardById(boardId);
        board.updateBoard(requestDto.getHostNickName());
        return board.getBoardId();
    }

    public void deleteBoard(Long id){
        Board board = boardRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("해당 id를 가진 Board를 찾을 수 없습니다. id="+id));
        boardRepository.delete(board);
    }
}
