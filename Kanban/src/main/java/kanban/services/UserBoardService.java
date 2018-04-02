package kanban.services;

import java.util.List;

import org.springframework.ui.Model;

import kanban.dto.UserBoardDto;
import kanban.model.UserBoard;

public interface UserBoardService extends CRUDService<UserBoard> {
	
	public List<UserBoardDto> findByUserId(int userId);
	
	public boolean hasAccessOrNot(int boardId, int userId, Model model);

}
