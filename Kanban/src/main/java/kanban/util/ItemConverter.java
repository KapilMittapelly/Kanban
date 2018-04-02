package kanban.util;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.springframework.stereotype.Component;

import kanban.dto.BoardDto;
import kanban.dto.ItemDto;
import kanban.model.Board;
import kanban.model.Item;

@Component
public class ItemConverter {
	
	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMMM, yyyy", Locale.ENGLISH);
	KanbanCustomDateEditor dateEditor = new KanbanCustomDateEditor(formatter, false);
	
	public List<ItemDto> modelListToDtoList (List<Item> itemList) {
		List<ItemDto> dtoList = new ArrayList<>();
		for(Item item : itemList) {
			ItemDto dto = new ItemDto();
			dto.setBoardId(item.getBoardId());
			dto.setItemId(item.getItemId());
			dto.setSequenceNum(item.getSequenceNum());
			dto.setItemDescription(item.getItemDescription());
			dto.setStatus(item.getStatus());
			dto.setPriority(item.getPriority());
			dto.setJiraNum(item.getJiraNum());
			dto.setUpdateTms(item.getUpdateTms());
			dto.setItemSummary(item.getItemSummary());
			dto.setDueDate(item.getDueDate());
			dto.setUpdateUser(item.getUpdateUser());
			dto.setAssignedUser(item.getAssignedUser());
			dto.setDueDateAsString(dateEditor.getAsString(item.getDueDate()));
			
			//Add board dto
			if(item.getBoard() != null) {
				BoardDto bDto = new BoardDto();
	    		bDto.setBoardId(item.getBoard().getBoardId());
	    		bDto.setBoardDescription(item.getBoard().getBoardDescription());
	    		bDto.setBoardKey(item.getBoard().getBoardKey());
	    		bDto.setBoardName(item.getBoard().getBoardName());
	    		dto.setBoardDto(bDto);
			}
			dtoList.add(dto);
		}
		return dtoList;
	}
	
	public List<Item> dtoListToModelList (List<ItemDto> dtoList) {
		List<Item> modelList = new ArrayList<Item>();
		for(ItemDto dto : dtoList) {
			Item item = new Item();
			item.setBoardId(dto.getBoardId());
			item.setItemId(dto.getItemId());
			item.setSequenceNum(dto.getSequenceNum());
			item.setItemDescription(dto.getItemDescription());
			item.setStatus(dto.getStatus());
			item.setPriority(dto.getPriority());
			item.setJiraNum(dto.getJiraNum());
			item.setUpdateTms(dto.getUpdateTms());
			item.setItemSummary(dto.getItemSummary());
			item.setDueDate(dto.getDueDate());
			item.setUpdateUser(dto.getUpdateUser());
			item.setAssignedUser(dto.getAssignedUser());
			
			//Add board
			if(dto.getBoardDto() != null) {
				Board board = new Board();
				board.setBoardId(dto.getBoardDto().getBoardId());
				board.setBoardDescription(dto.getBoardDto().getBoardDescription());
				board.setBoardKey(dto.getBoardDto().getBoardKey());
				board.setBoardName(dto.getBoardDto().getBoardName());
				item.setBoard(board);
			}
			modelList.add(item);
		}
		return modelList;
	}
	
	public ItemDto modelToDto(Item model) {
		ItemDto dto = new ItemDto();
		dto.setBoardId(model.getBoardId());
		dto.setItemId(model.getItemId());
		dto.setSequenceNum(model.getSequenceNum());
		dto.setItemDescription(model.getItemDescription());
		dto.setStatus(model.getStatus());
		dto.setPriority(model.getPriority());
		dto.setJiraNum(model.getJiraNum());
		dto.setUpdateTms(model.getUpdateTms());
		dto.setItemSummary(model.getItemSummary());
		dto.setDueDate(model.getDueDate());
		dto.setUpdateUser(model.getUpdateUser());
		dto.setAssignedUser(model.getAssignedUser());
		dto.setDueDateAsString(dateEditor.getAsString(model.getDueDate()));
		
		//Add board dto
		if(model.getBoard() != null) {
			BoardDto bDto = new BoardDto();
			bDto.setBoardId(model.getBoard().getBoardId());
			bDto.setBoardDescription(model.getBoard().getBoardDescription());
			bDto.setBoardKey(model.getBoard().getBoardKey());
			bDto.setBoardName(model.getBoard().getBoardName());
			dto.setBoardDto(bDto);
		}		
		return dto;
	}
	
	public Item dtoToModel(ItemDto dto) {
		Item model = new Item();
		model.setBoardId(dto.getBoardId());
		model.setItemId(dto.getItemId());
		model.setSequenceNum(dto.getSequenceNum());
		model.setItemDescription(dto.getItemDescription());
		model.setStatus(dto.getStatus());
		model.setPriority(dto.getPriority());
		model.setJiraNum(dto.getJiraNum());
		model.setUpdateTms(dto.getUpdateTms());
		model.setItemSummary(dto.getItemSummary());
		model.setDueDate(dto.getDueDate());
		model.setUpdateUser(dto.getUpdateUser());
		model.setAssignedUser(dto.getAssignedUser());
		
		//Add board 
		if(dto.getBoardDto() != null) {
			Board board = new Board();
			board.setBoardId(dto.getBoardDto().getBoardId());
			board.setBoardDescription(dto.getBoardDto().getBoardDescription());
			board.setBoardKey(dto.getBoardDto().getBoardKey());
			board.setBoardName(dto.getBoardDto().getBoardName());
			model.setBoard(board);
		}
		return model;
	}

}
