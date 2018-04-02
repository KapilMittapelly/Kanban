package kanban.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import kanban.dto.BoardDto;
import kanban.dto.UserBoardDto;
import kanban.services.BoardService;
import kanban.services.security.UserDetailsImpl;
import kanban.util.Constants;
import kanban.validation.BoardValidator;

@Controller
public class BoardsController {

	@Autowired
	private BoardService boardsService;

	/**
	 * GET /get-by-email --> Return the id for the user having the passed email.
	 */
	@RequestMapping("/get-by-board-id")
	@ResponseBody
	public String getByBoardId(Integer boardId) {
		String boardDescription = "";
		try {
			BoardDto board = boardsService.findByBoardId(boardId);
			boardDescription = String.valueOf(board.getBoardDescription());
		} catch (Exception ex) {
			return "No Board with that ID";
		}
		return "The board name is: " + boardDescription;
	}

	@RequestMapping(value = "/newBoard", method = RequestMethod.GET)
	public String createBoard(final Model model) {
		int loggedInUserId = ((UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUserId();
		final BoardDto boardDto = new BoardDto();
		UserBoardDto userBoardDto = new UserBoardDto(loggedInUserId, Constants.OWNER_ACCESS);
		List<UserBoardDto> userBoards = new ArrayList<UserBoardDto>();
		userBoards.add(userBoardDto);
		boardDto.setUserBoards(userBoards);
		model.addAttribute("board", boardDto);
		model.addAttribute(Constants.LOGGED_IN_USER_ID, loggedInUserId);
		return "newboard";
	}
	
	@RequestMapping(value = "/createBoard", params = { "addUser" })
	public String addUser(final Model model, @ModelAttribute("board") final BoardDto boardDto, Errors errors) {
		
		int loggedInUserId = ((UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUserId();
		
		new BoardValidator().validate(boardDto, errors);
		if(!errors.hasErrors()) {
			boardDto.getUserBoards().add(new UserBoardDto());
		}
		
		model.addAttribute("board", boardDto);
		model.addAttribute(Constants.LOGGED_IN_USER_ID, loggedInUserId);
		return "newboard";
	}
	
	@RequestMapping(value = "/createBoard", params = { "removeUser" })
	public String removeUser(final Model model,  @ModelAttribute("board") final BoardDto boardDto, Errors errors, HttpServletRequest req) {
		
		int loggedInUserId = ((UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUserId();		
		if(!errors.hasErrors()) {
			Integer rowId = Integer.valueOf(req.getParameter("removeUser"));
			boardDto.getUserBoards().remove(rowId.intValue());
		}
		model.addAttribute("board", boardDto);
		model.addAttribute(Constants.LOGGED_IN_USER_ID, loggedInUserId);
		return "newboard";
	}
	
	@RequestMapping(value = "/createBoard", params = { "create" }, method = RequestMethod.POST)
	public String createBoard(final Model model, @ModelAttribute("board") final BoardDto boardDto, Errors errors, HttpServletResponse response) throws IOException {
		
		int loggedInUserId = ((UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUserId();
		
		new BoardValidator().validate(boardDto, errors);
		if(!errors.hasErrors()) {
			final BoardDto board = boardsService.createBoard(boardDto);
			
			if(board == null) {
				System.err.println("something went wrong");
				model.addAttribute("board", boardDto);
				model.addAttribute(Constants.LOGGED_IN_USER_ID, loggedInUserId);
				return "newboard";
			}
		} else {
			System.err.println(errors.toString());
			model.addAttribute(Constants.LOGGED_IN_USER_ID, loggedInUserId);
			return "newboard";
		}
		response.sendRedirect("/myDashboard");
		return "newboard";
	}
	
	@RequestMapping(value = "/updateBoard", method = RequestMethod.GET)
	public String updateBoard(int boardId, int userId, final Model model) {
		
		int loggedInUserId = ((UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUserId();
		
		if(userId == loggedInUserId) {
			BoardDto boardDto = boardsService.findByBoardIdAndUserIdWithCreateAccess(boardId, userId);
			if(boardDto == null) {
				return "404";
			}
			model.addAttribute(Constants.LOGGED_IN_USER_ID, loggedInUserId);
			model.addAttribute("board", boardDto);
			return "updateboard";
		} else {
			return "404";
		}
	}
	
	@RequestMapping(value = "/updateBoard", params = { "addUser" }, method = RequestMethod.POST)
	public String addUserInUpdate(final Model model, @ModelAttribute("board") final BoardDto boardDto, Errors errors) {
		
		int loggedInUserId = ((UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUserId();
		
		new BoardValidator().validate(boardDto, errors);
		if(!errors.hasErrors()) {
			boardDto.getUserBoards().add(new UserBoardDto());
		}
		
		model.addAttribute(Constants.LOGGED_IN_USER_ID, loggedInUserId);
		model.addAttribute("board", boardDto);
		return "updateboard";
	}
	
	@RequestMapping(value = "/updateBoard", params = { "removeUser" }, method = RequestMethod.POST)
	public String removeUserInUpdate(final Model model,  @ModelAttribute("board") final BoardDto boardDto, Errors errors, HttpServletRequest req) {
		
		int loggedInUserId = ((UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUserId();
		
		if(!errors.hasErrors()) {
			Integer rowId = Integer.valueOf(req.getParameter("removeUser"));
			boardDto.getUserBoards().remove(rowId.intValue());
		}
		model.addAttribute(Constants.LOGGED_IN_USER_ID, loggedInUserId);
		model.addAttribute("board", boardDto);
		return "updateboard";
	}
	
	@RequestMapping(value = "/updateBoard", params = { "update" }, method = RequestMethod.POST)
	public String updateBoard(final Model model, @ModelAttribute("board") final BoardDto boardDto, Errors errors, HttpServletResponse response) throws IOException {
		
		int loggedInUserId = ((UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUserId();
		new BoardValidator().validate(boardDto, errors);
		if(!errors.hasErrors()) {
			final BoardDto board = boardsService.updateBoard(boardDto);
			
			if(board == null) {
				System.err.println("something went wrong");
				model.addAttribute(Constants.LOGGED_IN_USER_ID, loggedInUserId);
				model.addAttribute("board", boardDto);
				return "updateboard";
			}
		} else {
			System.err.println(errors.toString());
			model.addAttribute(Constants.LOGGED_IN_USER_ID, loggedInUserId);
			return "updateboard";
		}
		response.sendRedirect("/myDashboard");
		return "updateboard";
	}
	
	@RequestMapping(value = "/updateBoard", params = { "delete" }, method = RequestMethod.POST)
	public String deleteBoard(final Model model, @ModelAttribute("board") final BoardDto boardDto, HttpServletResponse response) throws IOException {
		
		try {
			boardsService.deleteBoard(boardDto);
			response.sendRedirect("/myDashboard");
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return "404";
	}
}
