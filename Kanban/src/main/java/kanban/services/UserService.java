package kanban.services;

import kanban.dto.UserDto;
import kanban.error.UserAlreadyExistException;
import kanban.model.User;

public interface UserService extends CRUDService<User> {
	
	public User findUserByEmail(String email);
	
	public UserDto findByEmail(String email);
	
	UserDto registerNewUserAccount(UserDto accountDto) throws UserAlreadyExistException;

	UserDto UpdateUserAccount(UserDto accountDto);
	
	public boolean isUserExists(int userID);

}
