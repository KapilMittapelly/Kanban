package kanban.services.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import kanban.dto.ItemDto;
import kanban.dto.UserBoardDto;
import kanban.model.UserBoard;
import kanban.repository.UserBoardsRepository;
import kanban.services.ItemService;
import kanban.services.UserBoardService;
import kanban.util.Constants;
import kanban.util.UserBoardConverter;

@Service
@Profile("springdatajpa")
public class UserBoardServiceImpl implements UserBoardService {
	
	@Autowired
	private UserBoardConverter userBoardConverter;
	
	@Autowired
	private ItemService itemService;
	
	private UserBoardsRepository userBoardsRepo;
	
	@Autowired
	public void setUserBoardsRepository(UserBoardsRepository userBoardsRepo) {
		this.userBoardsRepo = userBoardsRepo;
	}

	@Override
	public List<?> listAll() {
		List<UserBoard> userBoards = new ArrayList<>();
		userBoardsRepo.findAll().forEach(userBoards::add);
		return userBoards;
	}

	@Override
	public UserBoard saveOrUpdate(UserBoard domainObject) {
		return userBoardsRepo.save(domainObject);
		}

	@Override
	public List<UserBoardDto> findByUserId(int userId) {
		List<UserBoardDto> userboardDtos = userBoardConverter.modelToDto(userBoardsRepo.findByUserId(userId));
		
		for(UserBoardDto dto : userboardDtos) {
			//for each board, get the board items.
			List<ItemDto> items = itemService.findByBoardId(dto.getBoardDto().getBoardId());
			dto.getBoardDto().setItems(items);
		}
		
		return userboardDtos;
	}

	@Override
	public boolean hasAccessOrNot(int boardId, int userId, Model model) {
		boolean hasAccess = false;
		final List<UserBoardDto> userBoardDtos = this.findByUserId(userId);
		for(UserBoardDto dto : userBoardDtos) {
			if(dto.getBoardDto().getBoardId() == boardId) {
				model.addAttribute(Constants.BOARD_NAME, dto.getBoardDto().getBoardName());
				model.addAttribute(Constants.BOARD_KEY, dto.getBoardDto().getBoardKey().toUpperCase());
				model.addAttribute(Constants.ACCESS_LEVEL, dto.getAccessLevel());
				hasAccess = true;
				break;
			}
		}
		return hasAccess;
	}
}
