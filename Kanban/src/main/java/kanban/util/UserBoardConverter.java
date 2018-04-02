package kanban.util;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import kanban.dto.BoardDto;
import kanban.dto.UserBoardDto;
import kanban.model.Board;
import kanban.model.UserBoard;

@Component
public class UserBoardConverter {
	
	public List<UserBoardDto> modelToDto(List<UserBoard> userBoards) {
    	
    	List<UserBoardDto> dtos = new ArrayList<UserBoardDto>();
    	
    	for(UserBoard userBoard : userBoards) {
    		UserBoardDto dto = new UserBoardDto();
    		dto.setUserId(userBoard.getUserId());
    		dto.setAccessLevel(userBoard.getAccessLevel());
    		BoardDto bDto = new BoardDto();
    		bDto.setBoardId(userBoard.getBoard().getBoardId());
    		bDto.setBoardDescription(userBoard.getBoard().getBoardDescription());
    		bDto.setBoardKey(userBoard.getBoard().getBoardKey());
    		bDto.setBoardName(userBoard.getBoard().getBoardName());
    		dto.setBoardDto(bDto);
    		dtos.add(dto);
    	}
    	return dtos;
    }
	
	public List<UserBoard> dtoToModel(List<UserBoardDto> dtos) {
		
		List<UserBoard> models = new ArrayList<UserBoard>();
		
		for(UserBoardDto dto : dtos) {
			UserBoard model = new UserBoard();
			model.setUserId(dto.getUserId());
			model.setAccessLevel(dto.getAccessLevel());
			Board board = new Board();
			board.setBoardId(dto.getBoardDto().getBoardId());
			board.setBoardDescription(dto.getBoardDto().getBoardDescription());
			board.setBoardKey(dto.getBoardDto().getBoardKey());
			board.setBoardName(dto.getBoardDto().getBoardName());
			model.setBoard(board);
			models.add(model);
		}
		return models;
	}

}
