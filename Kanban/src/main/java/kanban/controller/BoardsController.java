package kanban.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import kanban.model.Board;
import kanban.repository.BoardsRepository;

@Controller
public class BoardsController {
	
	@Autowired
	private BoardsRepository boardsRepo;

	/**
	   * GET /get-by-email  --> Return the id for the user having the passed
	   * email.
	   */
	  @RequestMapping("/get-by-board-id")
	  @ResponseBody
	  public String getByBoardId(Integer boardId) {
	    String boardDescription = "";
	    try {
	      Board board = boardsRepo.findByBoardId(boardId);
	      boardDescription = String.valueOf(board.getBoardDescription());
	    }
	    catch (Exception ex) {
	      return "No Board with that ID";
	    }
	    return "The board name is: " + boardDescription;
	  }
}
