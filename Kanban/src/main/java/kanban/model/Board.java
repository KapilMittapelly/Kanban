package kanban.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "board")
public class Board {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "board_id")
	private int boardId;

	@NotNull
	@Column(name = "board_description")
	private String boardDescription;

	@NotNull
	@Column(name = "board_key")
	private String boardKey;
	
	@NotNull
	@Column(name = "board_name")
	private String boardName;
	
	@OneToMany(fetch=FetchType.LAZY, cascade = CascadeType.ALL, mappedBy="board", orphanRemoval=true)
	private List<UserBoard> userBoards;
	
	@OneToMany(fetch=FetchType.LAZY, cascade = CascadeType.ALL, mappedBy="board", orphanRemoval=true)
	private List<Item> boardItems;

	public int getBoardId() {
		return boardId;
	}

	public void setBoardId(int boardId) {
		this.boardId = boardId;
	}

	public String getBoardDescription() {
		return boardDescription;
	}

	public void setBoardDescription(String boardDescription) {
		this.boardDescription = boardDescription;
	}

	public List<UserBoard> getUserBoards() {
		return userBoards;
	}

	public void setUserBoards(List<UserBoard> userBoards) {
		this.userBoards = userBoards;
	}

	public String getBoardKey() {
		return boardKey;
	}

	public void setBoardKey(String boardKey) {
		this.boardKey = boardKey;
	}

	public String getBoardName() {
		return boardName;
	}

	public void setBoardName(String boardName) {
		this.boardName = boardName;
	}

	public List<Item> getBoardItems() {
		return boardItems;
	}

	public void setBoardItems(List<Item> boardItems) {
		this.boardItems = boardItems;
	}

	@Override
	public String toString() {
		return "Board [boardId=" + boardId + ", boardDescription=" + boardDescription + ", boardKey=" + boardKey
				+ ", boardName=" + boardName + ", userBoards=" + userBoards + ", boardItems=" + boardItems + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((boardDescription == null) ? 0 : boardDescription.hashCode());
		result = prime * result + boardId;
		result = prime * result + ((boardItems == null) ? 0 : boardItems.hashCode());
		result = prime * result + ((boardKey == null) ? 0 : boardKey.hashCode());
		result = prime * result + ((boardName == null) ? 0 : boardName.hashCode());
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
		Board other = (Board) obj;
		if (boardDescription == null) {
			if (other.boardDescription != null)
				return false;
		} else if (!boardDescription.equals(other.boardDescription))
			return false;
		if (boardId != other.boardId)
			return false;
		if (boardItems == null) {
			if (other.boardItems != null)
				return false;
		} else if (!boardItems.equals(other.boardItems))
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
		if (userBoards == null) {
			if (other.userBoards != null)
				return false;
		} else if (!userBoards.equals(other.userBoards))
			return false;
		return true;
	}
}
