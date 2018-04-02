package kanban.services.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import kanban.dto.ItemDto;
import kanban.model.Board;
import kanban.model.Item;
import kanban.repository.BoardsRepository;
import kanban.repository.ItemsRepository;
import kanban.services.ItemService;
import kanban.services.security.UserDetailsImpl;
import kanban.util.Constants;
import kanban.util.ItemConverter;

@Service
@Profile("springdatajpa")
public class ItemServiceImpl implements ItemService {
	
	@Autowired
	private ItemConverter itemConverter;
	
	private ItemsRepository itemRepo;
	
	@Autowired
	public void setItemRepo(ItemsRepository itemRepo) {
		this.itemRepo = itemRepo;
	}
	
	private BoardsRepository boardsRepo;
	
	@Autowired
	public void setBoardsRepo(BoardsRepository boardsRepo) {
		this.boardsRepo = boardsRepo;
	}

	@Override
	public List<?> listAll() {
		List<Item> items = new ArrayList<>();
		itemRepo.findAll().forEach(items::add);
		return items;
	}

	@Override
	public Item saveOrUpdate(Item item) {
		return itemRepo.save(item);
	}

	@Override
	public List<ItemDto> findByItemId(int itemId) {
		return itemConverter.modelListToDtoList(itemRepo.findByItemId(itemId));
	}

	@Override
	public List<ItemDto> findByBoardId(int boardId) {
		return itemConverter.modelListToDtoList(itemRepo.findByBoardIdAndMaxSeqNum(boardId, boardId));
	}
	
	@Override
	public List<ItemDto> findByBoardIdAndFilterByUserId(int boardId, int userId, boolean filterFlag) {
		List<ItemDto> itemDtos = this.findByBoardId(boardId);
		if(filterFlag) {
			List<ItemDto> filteredItemDtos = itemDtos.stream()
			.filter(itemdto -> userId == itemdto.getAssignedUser())
			.collect(Collectors.toList());
			return filteredItemDtos;
		}
		return itemDtos;
	}

	@Transactional
	@Override
	public ItemDto createItem(ItemDto itemDto) {
		Board board = boardsRepo.findByBoardId(itemDto.getBoardId());
		Item itemFromDB = itemRepo.findTopByBoardOrderByItemIdDesc(board);
		itemDto.setItemId(itemFromDB == null ? Constants.INITIAL_ITEM_ID : itemFromDB.getItemId()+1);
		itemDto.setSequenceNum(Constants.INITIAL_SEQ_NUM);
		if(itemRepo.findByBoardAndItemIdAndSequenceNum(board, itemDto.getItemId(), itemDto.getSequenceNum()) == null) {
			return itemConverter.modelToDto(itemRepo.save(itemConverter.dtoToModel(itemDto)));
		} else {
			throw new RuntimeException();
		}
	}
	
	@Transactional
	@Override
	public ItemDto updateItem(ItemDto itemDto) {
		Board board = boardsRepo.findByBoardId(itemDto.getBoardId());
		Item itemFromDB = itemRepo.findTopByBoardAndItemIdOrderBySequenceNumDesc(board, itemDto.getItemId());
		if(itemFromDB == null) {
			throw new RuntimeException();
		} else {
			itemDto.setSequenceNum(itemDto.getSequenceNum()+1);
			itemDto.setUpdateUser(((UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUserId());
			return itemConverter.modelToDto(itemRepo.save(itemConverter.dtoToModel(itemDto)));
		}
	}

	@Transactional
	@Override
	public ItemDto updateItemStatus(ItemDto itemDto) {
		
		Board board = boardsRepo.findByBoardId(itemDto.getBoardId());
		ItemDto itemToSave = itemConverter.modelToDto(itemRepo.findTopByBoardAndItemIdOrderBySequenceNumDesc(board, itemDto.getItemId()));
		itemToSave.setSequenceNum(itemToSave.getSequenceNum()+1);
		itemToSave.setStatus(itemDto.getStatus());
		itemToSave.setUpdateTms(null);
		itemToSave.setUpdateUser(((UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUserId());
		
		if(itemRepo.findByBoardAndItemIdAndSequenceNum(board, itemToSave.getItemId(), itemToSave.getSequenceNum()) == null) {
			return itemConverter.modelToDto(itemRepo.save(itemConverter.dtoToModel(itemToSave)));
		} else {
			throw new RuntimeException();
		}
	}
	
	@Transactional
	@Override
	public void deleteItem(ItemDto itemDto) {
		
		Board board = boardsRepo.findByBoardId(itemDto.getBoardId());
		List<Item> itemsToDelete = itemRepo.findByBoardAndItemId(board, itemDto.getItemId());
		//Item item = itemConverter.dtoToModel(itemDto);
        itemRepo.delete(itemsToDelete);
    	
    }

	/*@Override
	public ItemDto findTopByBoardIdAndItemIdOrderBySequenceNumDesc(int boardId, int itemId) {
		Item item = itemRepo.findTopByBoardAndItemIdOrderBySequenceNumDesc(boardId, itemId);
		return item == null ? null : itemConverter.modelToDto(item);
	}*/

}
