package kanban.util;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import kanban.dto.BoardDto;
import kanban.dto.UserBoardDto;
import kanban.model.Board;
import kanban.model.Item;
import kanban.model.UserBoard;

@Component
public class BoardConverter {
	
	public Board boardDtoToBoard(BoardDto boardDto) {
		
		Board board = new Board();
		board.setBoardId(boardDto.getBoardId());
		board.setBoardDescription(boardDto.getBoardDescription());
		board.setBoardKey(boardDto.getBoardKey());
		board.setBoardName(boardDto.getBoardName());
		List<UserBoard> userBoards = new ArrayList<UserBoard>();
		for(UserBoardDto userBoardDto : boardDto.getUserBoards()) {
			UserBoard userBoard = new UserBoard();
        	userBoard.setUserId(userBoardDto.getUserId());
        	userBoard.setAccessLevel(userBoardDto.getAccessLevel());
        	userBoard.setBoard(board);
        	userBoards.add(userBoard);
		}
		board.setUserBoards(userBoards);
		board.setBoardItems(new ArrayList<Item>());
		return board;
	}
	
	public BoardDto boardToBoardDto(Board board) {
		
		BoardDto boardDto = new BoardDto();
		boardDto.setBoardId(board.getBoardId());
		boardDto.setBoardDescription(board.getBoardDescription());
		boardDto.setBoardKey(board.getBoardKey());
		boardDto.setBoardName(board.getBoardName());
		List<UserBoardDto> userBoardDtos = new ArrayList<UserBoardDto>();
		for(UserBoard userBoard : board.getUserBoards()) {
			UserBoardDto userBoardDto = new UserBoardDto();
			userBoardDto.setUserId(userBoard.getUserId());
			userBoardDto.setAccessLevel(userBoard.getAccessLevel());
			userBoardDtos.add(userBoardDto);
		}
		boardDto.setUserBoards(userBoardDtos);
		return boardDto;
	}

}
