package kanban.validation;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import kanban.dto.UserBoardDto;
import kanban.util.BeanUtil;

public class UserBoardValidator implements Validator {
	
	
	public boolean supports(Class<?> clazz) {
		return clazz.equals(UserBoardDto.class);
	}

	public void validate(Object obj, Errors errors) {
		UserBoardDto userBoard = (UserBoardDto)obj;
		Validator validator = BeanUtil.getBean(org.springframework.validation.Validator.class);
		ValidationUtils.invokeValidator(validator, userBoard, errors);
	}

}