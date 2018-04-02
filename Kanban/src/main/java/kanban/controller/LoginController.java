package kanban.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import kanban.dto.UserBoardDto;
import kanban.dto.UserDto;
import kanban.services.UserBoardService;
import kanban.services.UserService;
import kanban.services.security.UserDetailsImpl;

@Controller
public class LoginController {
	
	@Autowired
    private UserService userService;
	
	@Autowired
    private UserBoardService userBoardService;
	
	@RequestMapping(value ="/", method = RequestMethod.GET)
	public String getHome(final Model model, HttpServletResponse response) throws IOException {
		if(SecurityContextHolder.getContext().getAuthentication().getPrincipal() != null && 
				!SecurityContextHolder.getContext().getAuthentication().getPrincipal().equals("anonymousUser")) {
			response.sendRedirect("/myDashboard");
			return "404";
		} else {
			final UserDto accountDto = new UserDto();
	        model.addAttribute("user", accountDto);
			return "index";
		}
	}
	
	@RequestMapping(value ="/login", method = RequestMethod.GET)
	public String getLogin(final Model model) {
		final UserDto accountDto = new UserDto();
        model.addAttribute("user", accountDto);
		return "index";
	}
	
	@RequestMapping(value ="/myProfile", method = RequestMethod.GET)
	public String getProfile(final HttpServletRequest request, final Model model) {
		final UserDto userDto = userService.findByEmail(((UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getEmail());
		model.addAttribute("user", userDto);		
        return "profile";
	}
	
	@RequestMapping(value ="/myDashboard", method = RequestMethod.GET)
	public String getDashboard(final HttpServletRequest request, final Model model) {
		
		final List<UserBoardDto> userBoardDtos = userBoardService.findByUserId(((UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUserId());
		model.addAttribute("userboards", userBoardDtos);
        return "dashboard";
	}
	
	@RequestMapping(value = "/signup", method = RequestMethod.POST)
    public ModelAndView registerUserAccount(@ModelAttribute("user") @Valid final UserDto userDto, BindingResult result, 
    		HttpServletRequest request, HttpServletResponse response) throws Exception {
		
	    if (result.hasErrors()) {
	    	return new ModelAndView("index", "user", userDto);
	    }
		final UserDto registered = userService.registerNewUserAccount(userDto);
		if (registered == null) {
            return new ModelAndView("index", "user", userDto);
        }
		request.login(userDto.getEmail(), userDto.getPassword());
		return new ModelAndView("dashboard", "userboards", null);
	}
	
    @RequestMapping(value = "/updateProfile", method = RequestMethod.POST)
    public ModelAndView changeUserPassword(final Model model, @ModelAttribute("user") @Valid final UserDto userDto, BindingResult result) {
    	
    	if (result.hasErrors()) {
	    	return new ModelAndView("profile", "user", userDto);
	    }
    	
        userService.UpdateUserAccount(userDto);
        model.addAttribute("message", "Password updated succesfully");
        return new ModelAndView("profile", "user", userDto);
    }
}
