package kanban.dto;

import java.sql.Date;
import java.sql.Timestamp;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import kanban.util.Constants;
import kanban.validation.ValidUserID;

public class ItemDto {

	private int boardId;

	private int itemId;
	
	private int sequenceNum;
	
	@NotNull
	@Size.List ({
		@Size(min = 1, message="Item summary cannot be empty"),
		@Size(max = 100, message="Item summary must be less than {max} characters")
	})
	private String itemSummary;
	
	@NotNull
	@Size.List({
		@Size(min = 1, message="Item Description cannot be empty"),
		@Size(max = 500, message="Item Description must be less than {max} characters")
	})
	private String itemDescription;
	
	@NotNull
	private short status = 1;
	
	@NotNull
	@Min(value=1, message="Invalid Priority")
	@Max(value=3, message="Invalid Priority")
	private Short priority;
	
	@NotNull
	@Size(min = 1, message="JIRA Number cannot be empty")
	private String jiraNum;
	
	private Timestamp updateTms;
	
	private int updateUser;
	
	@NotNull
	private Date dueDate;
	
	private String dueDateAsString;
	
	@NotNull
	@ValidUserID
	private int assignedUser;
	
	private BoardDto boardDto;

	public int getBoardId() {
		return boardId;
	}

	public void setBoardId(int boardId) {
		this.boardId = boardId;
	}

	public int getItemId() {
		return itemId;
	}

	public void setItemId(int itemId) {
		this.itemId = itemId;
	}

	public int getSequenceNum() {
		return sequenceNum;
	}

	public void setSequenceNum(int sequenceNum) {
		this.sequenceNum = sequenceNum;
	}

	public String getItemSummary() {
		return itemSummary;
	}

	public void setItemSummary(String itemSummary) {
		this.itemSummary = itemSummary;
	}

	public String getItemDescription() {
		return itemDescription;
	}

	public void setItemDescription(String itemDescription) {
		this.itemDescription = itemDescription;
	}

	public short getStatus() {
		return status;
	}

	public void setStatus(short status) {
		this.status = status;
	}

	public Short getPriority() {
		return (priority == null) ? 0 : priority;
	}

	public void setPriority(Short priority) {
		this.priority = priority;
	}

	public String getJiraNum() {
		return jiraNum;
	}

	public void setJiraNum(String jiraNum) {
		this.jiraNum = jiraNum;
	}

	public Timestamp getUpdateTms() {
		return updateTms;
	}

	public void setUpdateTms(Timestamp updateTms) {
		this.updateTms = updateTms;
	}

	public int getUpdateUser() {
		return updateUser;
	}

	public void setUpdateUser(int updateUser) {
		this.updateUser = updateUser;
	}

	public Date getDueDate() {
		return dueDate;
	}

	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}

	public int getAssignedUser() {
		return assignedUser;
	}

	public void setAssignedUser(int assignedUser) {
		this.assignedUser = assignedUser;
	}
	
	public String getDueDateAsString() {
		return dueDateAsString;
	}

	public void setDueDateAsString(String dueDateAsString) {
		this.dueDateAsString = dueDateAsString;
	}

	public BoardDto getBoardDto() {
		return boardDto;
	}

	public void setBoardDto(BoardDto boardDto) {
		this.boardDto = boardDto;
	}

	public String getPriorityAsString() {
		switch(this.getPriority()) {
			case Constants.LOW_PRIORITY: return Constants.LOW_PRIORITY_STRING;
			case Constants.MEDIUM_PRIORITY: return Constants.MEDIUM_PRIORITY_STRING;
			case Constants.HIGH_PRIORITY: return Constants.HIGH_PRIORITY_STRING;
			default: return Constants.LOW_PRIORITY_STRING;
		}
	}

	@Override
	public String toString() {
		return "ItemDto [boardId=" + boardId + ", itemId=" + itemId + ", sequenceNum=" + sequenceNum + ", itemSummary="
				+ itemSummary + ", itemDescription=" + itemDescription + ", status=" + status + ", priority=" + priority
				+ ", jiraNum=" + jiraNum + ", updateTms=" + updateTms + ", updateUser=" + updateUser + ", dueDate="
				+ dueDate + ", assignedUser=" + assignedUser + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + assignedUser;
		result = prime * result + boardId;
		result = prime * result + ((dueDate == null) ? 0 : dueDate.hashCode());
		result = prime * result + ((itemDescription == null) ? 0 : itemDescription.hashCode());
		result = prime * result + itemId;
		result = prime * result + ((itemSummary == null) ? 0 : itemSummary.hashCode());
		result = prime * result + ((jiraNum == null) ? 0 : jiraNum.hashCode());
		result = prime * result + ((priority == null) ? 0 : priority.hashCode());
		result = prime * result + sequenceNum;
		result = prime * result + status;
		result = prime * result + ((updateTms == null) ? 0 : updateTms.hashCode());
		result = prime * result + updateUser;
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
		ItemDto other = (ItemDto) obj;
		if (assignedUser != other.assignedUser)
			return false;
		if (boardId != other.boardId)
			return false;
		if (dueDate == null) {
			if (other.dueDate != null)
				return false;
		} else if (!dueDate.equals(other.dueDate))
			return false;
		if (itemDescription == null) {
			if (other.itemDescription != null)
				return false;
		} else if (!itemDescription.equals(other.itemDescription))
			return false;
		if (itemId != other.itemId)
			return false;
		if (itemSummary == null) {
			if (other.itemSummary != null)
				return false;
		} else if (!itemSummary.equals(other.itemSummary))
			return false;
		if (jiraNum == null) {
			if (other.jiraNum != null)
				return false;
		} else if (!jiraNum.equals(other.jiraNum))
			return false;
		if (priority == null) {
			if (other.priority != null)
				return false;
		} else if (!priority.equals(other.priority))
			return false;
		if (sequenceNum != other.sequenceNum)
			return false;
		if (status != other.status)
			return false;
		if (updateTms == null) {
			if (other.updateTms != null)
				return false;
		} else if (!updateTms.equals(other.updateTms))
			return false;
		if (updateUser != other.updateUser)
			return false;
		return true;
	}
}
