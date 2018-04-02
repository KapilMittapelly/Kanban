package kanban.services;

import kanban.dto.BoardDto;
import kanban.model.Board;

public interface BoardService extends CRUDService<Board> {
	
	public BoardDto findByBoardId(int boardId);
	
	public BoardDto findByBoardIdAndUserIdWithCreateAccess(int boardId, int userId);
	
	public BoardDto createBoard(BoardDto boardDto);

	public BoardDto updateBoard(BoardDto boardDto);
	
	public void deleteBoard(BoardDto boardDto);

}

