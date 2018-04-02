package kanban.dto;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import kanban.validation.ValidUserID;

public class UserBoardDto {
	
	public UserBoardDto() {};

	public UserBoardDto(Integer userId, Integer accessLevel) {
		super();
		this.userId = userId;
		this.accessLevel = accessLevel;
	}

	@ValidUserID
	private Integer userId;

	@NotNull
	@Min(value=1, message="Invalid Access level")
	@Max(value=4, message="Invalid Access level")
	private Integer accessLevel;
	
	private BoardDto boardDto;

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getAccessLevel() {
		return accessLevel;
	}

	public void setAccessLevel(Integer accessLevel) {
		this.accessLevel = accessLevel;
	}

	public BoardDto getBoardDto() {
		return boardDto;
	}

	public void setBoardDto(BoardDto boardDto) {
		this.boardDto = boardDto;
	}

	@Override
	public String toString() {
		return "UserBoardDto [userId=" + userId + ", accessLevel=" + accessLevel + ", boardDto=" + boardDto + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((accessLevel == null) ? 0 : accessLevel.hashCode());
		result = prime * result + ((boardDto == null) ? 0 : boardDto.hashCode());
		result = prime * result + ((userId == null) ? 0 : userId.hashCode());
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
		UserBoardDto other = (UserBoardDto) obj;
		if (accessLevel == null) {
			if (other.accessLevel != null)
				return false;
		} else if (!accessLevel.equals(other.accessLevel))
			return false;
		if (boardDto == null) {
			if (other.boardDto != null)
				return false;
		} else if (!boardDto.equals(other.boardDto))
			return false;
		if (userId == null) {
			if (other.userId != null)
				return false;
		} else if (!userId.equals(other.userId))
			return false;
		return true;
	}
}
