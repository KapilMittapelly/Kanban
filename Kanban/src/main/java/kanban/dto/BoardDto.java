package kanban.dto;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class BoardDto {
	
	private int boardId;
	
	@NotNull
	@Size(min = 1, message="Description cannot be empty")
	//@Pattern(regexp="[0-9a-zA-Z]")
	//TODO have to add a validation to make sure the board names are valid, right now it takes any special characters without any alphabets
	private String boardDescription;
	
	@NotNull
	@Size(min = 1, message="Board Name cannot be empty")
	private String boardName;
	
	@NotNull
	@Size(min = 1, max = 5, message="Board Key cannot be empty")
	private String boardKey;
	
	private List<UserBoardDto> userBoards = new ArrayList<UserBoardDto>();
	
	private List<ItemDto> items = new ArrayList<ItemDto>();
	
	public int getBoardId() {
		return boardId;
	}

	public void setBoardId(int boardId) {
		this.boardId = boardId;
	}

	public String getBoardName() {
		return boardName;
	}

	public void setBoardName(String boardName) {
		this.boardName = boardName;
	}

	public String getBoardDescription() {
		return boardDescription;
	}

	public void setBoardDescription(String boardDescription) {
		this.boardDescription = boardDescription;
	}

	public List<UserBoardDto> getUserBoards() {
		return userBoards;
	}

	public void setUserBoards(List<UserBoardDto> userBoards) {
		this.userBoards = userBoards;
	}

	public String getBoardKey() {
		return boardKey;
	}

	public void setBoardKey(String boardKey) {
		this.boardKey = boardKey;
	}

	public List<ItemDto> getItems() {
		return items;
	}

	public void setItems(List<ItemDto> items) {
		this.items = items;
	}

	@Override
	public String toString() {
		return "BoardDto [boardId=" + boardId + ", boardDescription=" + boardDescription + ", boardName=" + boardName
				+ ", boardKey=" + boardKey + ", userBoards=" + userBoards + ", items=" + items + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((boardDescription == null) ? 0 : boardDescription.hashCode());
		result = prime * result + boardId;
		result = prime * result + ((boardKey == null) ? 0 : boardKey.hashCode());
		result = prime * result + ((boardName == null) ? 0 : boardName.hashCode());
		result = prime * result + ((items == null) ? 0 : items.hashCode());
		result = prime * result + ((userBoards == null) ? 0 : userBoards.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BoardDto other = (BoardDto) obj;
		if (boardDescription == null) {
			if (other.boardDescription != null)
				return false;
		} else if (!boardDescription.equals(other.boardDescription))
			return false;
		if (boardId != other.boardId)
			return false;
		if (boardKey == null) {
			if (other.boardKey != null)
				return false;
		} else if (!boardKey.equals(other.boardKey))
			return false;
		if (boardName == null) {
			if (other.boardName != null)
				return false;
		} else if (!boardName.equals(other.boardName))
			return false;
		if (items == null) {
			if (other.items != null)
				return false;
		} else if (!items.equals(other.items))
			return false;
		if (userBoards == null) {
			if (other.userBoards != null)
				return false;
		} else if (!userBoards.equals(other.userBoards))
			return false;
		return true;
	}
}
