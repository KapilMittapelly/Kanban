package kanban.services.impl;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import kanban.dto.UserDto;
import kanban.error.UserAlreadyExistException;
import kanban.model.User;
import kanban.repository.UserRepository;
import kanban.services.UserService;
import kanban.util.UserToUserDetails;

@Service
@Profile("springdatajpa")
public class UserServiceImpl implements UserService {

	@Autowired
	private UserToUserDetails userConverter;
	
	private UserRepository userRepository;

	@Autowired
	public void setUserRepository(UserRepository userRepository) {
		this.userRepository = userRepository;
	}
	
	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public List<?> listAll() {
		List<User> users = new ArrayList<>();
		userRepository.findAll().forEach(users::add);
		return users;
	}

	@Override
	public User saveOrUpdate(User domainObject) {
		if (domainObject.getPassword() != null) {
			domainObject.setPassword(passwordEncoder.encode(domainObject.getPassword()));
		}
		return userRepository.save(domainObject);
	}

	@Transactional
	public void delete(String id) {
		userRepository.delete(id);
	}
	
	@Override
	public UserDto findByEmail(String email) {
		return userConverter.userToUserDto(userRepository.findByEmail(email));
	}
	
	@Override
	public User findUserByEmail(String email) {
		return userRepository.findByEmail(email);
	}
	
    @Override
    public UserDto registerNewUserAccount(final UserDto accountDto) {
        if (emailExists(accountDto.getEmail())) {
            throw new UserAlreadyExistException("There is an account with that email adress: " + accountDto.getEmail());
        }
        
        final User user = new User();
        user.setName(accountDto.getName());
        user.setPassword(passwordEncoder.encode(accountDto.getPassword()));
        user.setEmail(accountDto.getEmail());
        user.setRole("USER");
        user.setEnabled(true);
        return userConverter.userToUserDto(userRepository.save(user));
    }
    
    @Override
    public UserDto UpdateUserAccount(final UserDto accountDto) {
    	
    	final User user = userRepository.findByEmail(accountDto.getEmail());
        user.setPassword(passwordEncoder.encode(accountDto.getPassword()));
        return userConverter.userToUserDto(userRepository.save(user));
    	
    }
    
    private boolean emailExists(final String email) {
        return userRepository.findByEmail(email) != null;
    }

	@Override
	public boolean isUserExists(int userID) {
		return userRepository.findByUserId(userID) != null;
	}

}
