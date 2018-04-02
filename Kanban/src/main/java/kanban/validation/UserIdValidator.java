package kanban.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import kanban.services.UserService;

public class UserIdValidator implements ConstraintValidator<ValidUserID, Integer> {
	
	@Autowired
    private UserService userService;
	
	@Override
	public void initialize(ValidUserID constraintAnnotation) {
	}

	@Override
	public boolean isValid(Integer userID, ConstraintValidatorContext context) {
		return userID == null ? false : this.isUserExists(userID);
	}
	
	private boolean isUserExists(int userID) {
		return userService.isUserExists(userID);
	}

}
