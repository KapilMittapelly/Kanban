package kanban.controller;

import java.io.IOException;
import java.sql.Date;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import kanban.dto.ItemDto;
import kanban.model.Item;
import kanban.services.ItemService;
import kanban.services.UserBoardService;
import kanban.services.security.UserDetailsImpl;
import kanban.util.KanbanCustomDateEditor;

@Controller
public class ItemController {
	
	@Autowired
	private ItemService itemService;
	
	@Autowired
    private UserBoardService userBoardService;
	
    @InitBinder
	public void dataBinding(WebDataBinder binder) {
    	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d MMMM, yyyy", Locale.ENGLISH);
		binder.registerCustomEditor(Date.class, "dueDate", new KanbanCustomDateEditor(formatter, false));
	}
	
	@RequestMapping(value = "/kanban", method = RequestMethod.GET)
	public String getKanban(int boardId, int userId, boolean assignedToMe, final Model model, HttpSession session) {
		
		if(userId == ((UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUserId()) {
			
			//check if the user has access to the given board.
			if(userBoardService.hasAccessOrNot(boardId, userId, model)) {
				//for adding new item
				final ItemDto itemDto = new ItemDto();
				itemDto.setBoardId(boardId);
				itemDto.setUpdateUser(userId);
				model.addAttribute("item", itemDto);
				
				//for displaying existing items
				List<ItemDto> items = itemService.findByBoardIdAndFilterByUserId(boardId, userId, assignedToMe);
				model.addAttribute("items", items);
				
				List<ItemDto> backlog = new ArrayList<ItemDto>(), todo = new ArrayList<ItemDto>(), wip = new ArrayList<ItemDto>(), done = new ArrayList<ItemDto>();
				
				for (ItemDto item : items) {
					switch (item.getStatus()) {
					case 1:
						backlog.add(item);
						break;
					case 2:
						todo.add(item);
						break;
					case 3:
						wip.add(item);
						break;
					case 4:
						done.add(item);
						break;
					}
				}
				
				model.addAttribute("backlog", backlog);
				model.addAttribute("todo", todo);
				model.addAttribute("wip", wip);
				model.addAttribute("done", done);
				model.addAttribute("assignedToMe", assignedToMe);
				session.setAttribute("model", model);
				
				return "kanban";
			} else {
				return "404";
			}
		} else {
			return "404";
		}
	}
	
	@RequestMapping(value = "/addItem", params = { "add" }, method = RequestMethod.POST)
	public String createItem(final Model model, @ModelAttribute("item") @Valid final ItemDto itemDto,
			BindingResult result, HttpServletResponse response, HttpSession session, HttpServletRequest req) throws Exception {		
		
		if (result.hasErrors()) {
			model.addAttribute("Add_Operation", true);
			Model modelFromSession = (Model) session.getAttribute("model");
			model.mergeAttributes(modelFromSession.asMap());
		} else {
			final ItemDto dto = itemService.createItem(itemDto);
			if (dto == null) {
				System.err.println("exception in insertion");
				throw new Exception();
			} else {
				response.sendRedirect("/kanban?boardId=" + dto.getBoardId() + "&userId=" + dto.getUpdateUser());
			}
		}
		
		return "kanban";
	}
	
	@RequestMapping(value = "/addItem", params = { "update" }, method = RequestMethod.POST)
	public String updateItem(final Model model, @ModelAttribute("item") @Valid final ItemDto itemDto,
			BindingResult result, HttpServletResponse response, HttpSession session, HttpServletRequest req) throws Exception {	
		
		if (result.hasErrors()) {
			model.addAttribute("Add_Operation", false);
			Model modelFromSession = (Model) session.getAttribute("model");
			model.mergeAttributes(modelFromSession.asMap());
		} else {
			final ItemDto dto = itemService.updateItem(itemDto);
			if (dto == null) {
				System.err.println("exception in insertion");
				throw new Exception();
			} else {
				response.sendRedirect("/kanban?boardId=" + dto.getBoardId() + "&userId=" + dto.getUpdateUser());
			}
		}
		
		return "kanban";
	}
	
	@RequestMapping(value = "/addItem", params = { "delete" }, method = RequestMethod.POST)
	public String deleteItem(final Model model, @ModelAttribute("item") @Valid final ItemDto itemDto,
			BindingResult result, HttpServletResponse response, HttpSession session, HttpServletRequest req) throws Exception {		
		
		if (result.hasErrors()) {
			model.addAttribute("Add_Operation", false);
			Model modelFromSession = (Model) session.getAttribute("model");
			model.mergeAttributes(modelFromSession.asMap());
		} else {
			itemService.deleteItem(itemDto);
			response.sendRedirect("/myDashboard");
		}
		
		return "kanban";
	}
	
	@RequestMapping("/updateItem")
	@ResponseBody
	public Item updateItemStatus(@RequestBody ItemDto itemDto,  HttpServletResponse response) throws IOException {
		try {
			if(itemDto == null) {
				System.err.println("null received");
			} else {
				itemService.updateItemStatus(itemDto);
			}
			return new Item();
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}
}
