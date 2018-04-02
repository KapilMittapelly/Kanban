package kanban.services.impl;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import kanban.dto.BoardDto;
import kanban.dto.UserBoardDto;
import kanban.model.Board;
import kanban.model.Item;
import kanban.repository.BoardsRepository;
import kanban.repository.ItemsRepository;
import kanban.services.BoardService;
import kanban.services.security.UserDetailsImpl;
import kanban.util.BoardConverter;
import kanban.util.Constants;

@Service
@Profile("springdatajpa")
public class BoardServiceImpl implements BoardService {
	
	@Autowired
	private BoardConverter boardConverter;
	
	private BoardsRepository boardsRepo;
	
	@Autowired
	public void setBoardsRepository(BoardsRepository boardsRepo) {
		this.boardsRepo = boardsRepo;
	}
	
	private ItemsRepository itemsRepo;
	
	@Autowired
	public void setItemsRepository(ItemsRepository itemsRepo) {
		this.itemsRepo = itemsRepo;
	}

	@Override
	public List<?> listAll() {
		List<Board> boards = new ArrayList<>();
		boardsRepo.findAll().forEach(boards::add);
		return boards;
	}

	@Override
	public Board saveOrUpdate(Board board) {
		return boardsRepo.save(board);
	}

	@Override
	public BoardDto findByBoardId(int boardId) {
		return boardConverter.boardToBoardDto(boardsRepo.findByBoardId(boardId));
	}
	
	@Override
	public BoardDto findByBoardIdAndUserIdWithCreateAccess (int boardId, int userId) {
		BoardDto boardDto = this.findByBoardId(boardId);
		for(UserBoardDto dto : boardDto.getUserBoards()) {
			if(userId == dto.getUserId()) {
				if(Constants.OWNER_ACCESS == dto.getAccessLevel()) {
					return boardDto;
				}
			}
		}
		return null;
	}

	@Transactional
	@Override
	public BoardDto createBoard(BoardDto boardDto) {
		
		boardDto = this.addOwnerIfNotExists(boardDto);
        Board board = boardConverter.boardDtoToBoard(boardDto);
        return boardConverter.boardToBoardDto(boardsRepo.save(board));
    
	}

	@Transactional
	@Override
	public BoardDto updateBoard(BoardDto boardDto) {
		
		//final Board board = boardsRepo.findByBoardId(boardDto.getBoardId());
		//TODO set the new values from boardDto to board before saving
		Board board = boardConverter.boardDtoToBoard(boardDto);
		List<Item> itemsFromDB = itemsRepo.findByBoard(board);
		board.setBoardItems(itemsFromDB);
        return boardConverter.boardToBoardDto(boardsRepo.save(board));
    	
    }
	
	@Transactional
	@Override
	public void deleteBoard(BoardDto boardDto) {
		
		Board board = boardConverter.boardDtoToBoard(boardDto);
        boardsRepo.delete(board);
    	
    }
	
	private BoardDto addOwnerIfNotExists(BoardDto boardDto) {
		
		int loggedInUserId = ((UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUserId();
		
		if(!checkIfLoggedInUserExists(boardDto, loggedInUserId)) {
			UserBoardDto dto = new UserBoardDto();
			dto.setUserId(loggedInUserId);
			dto.setAccessLevel(Constants.OWNER_ACCESS);
			boardDto.getUserBoards().add(dto);
		}
		return boardDto;
	}
	
	private boolean checkIfLoggedInUserExists(BoardDto boardDto, int loggedInUserId) {
		
		boolean userExists = false;
		for(UserBoardDto dto : boardDto.getUserBoards()) {
			if(dto.getUserId() == loggedInUserId) {
				dto.setAccessLevel(Constants.OWNER_ACCESS);
				userExists = true;
			}
		}
		return userExists;
	}

}
