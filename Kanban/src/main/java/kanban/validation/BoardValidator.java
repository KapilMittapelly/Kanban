package kanban.validation;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import kanban.dto.BoardDto;
import kanban.dto.UserBoardDto;
import kanban.util.BeanUtil;

public class BoardValidator implements Validator {

	public boolean supports(Class<?> clazz) {
		return clazz.equals(BoardDto.class);
	}

	public void validate(Object obj, Errors errors) {
		BoardDto board = (BoardDto) obj;
		List<UserBoardDto> userBoardList = board.getUserBoards();
		Validator validator = BeanUtil.getBean(org.springframework.validation.Validator.class);
		ValidationUtils.invokeValidator(validator, board, errors);
		
		int i=0;
		for (UserBoardDto userBoard : userBoardList) {
			errors.pushNestedPath("userBoards["+i+"]");
			ValidationUtils.invokeValidator(validator, userBoard, errors);
			errors.popNestedPath();
			i++;
		}
		
		//Remove if duplicate rows are added.
		Set<Integer> set = new HashSet<Integer>();
		for(int x=0; x<userBoardList.size(); x++) {
			boolean valid = set.add(userBoardList.get(x).getUserId());
			if(!valid) {
				userBoardList.remove(x);
			}
		}
	}
}
