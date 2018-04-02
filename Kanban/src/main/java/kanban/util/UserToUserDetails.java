package kanban.util;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import kanban.dto.UserDto;
import kanban.model.User;
import kanban.services.security.UserDetailsImpl;

@Component
public class UserToUserDetails implements Converter<User, UserDetails> {
    @Override
    public UserDetails convert(User user) {
        UserDetailsImpl userDetails = new UserDetailsImpl();

        if (user != null) {
            userDetails.setUserId(user.getUserId());
            userDetails.setEmail(user.getEmail());
            userDetails.setPassword(user.getPassword());
            userDetails.setEnabled(user.isEnabled());
            userDetails.setName(user.getName());
            Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
            authorities.add(new SimpleGrantedAuthority(user.getRole()));
            userDetails.setAuthorities(authorities);
        }

        return userDetails;
    }
    
    public UserDto userToUserDto (User user) {
    	
    	UserDto userDto = new UserDto();
    	userDto.setUserId(user.getUserId());
    	userDto.setEmail(user.getEmail());
    	userDto.setName(user.getName());
    	userDto.setRole(user.getRole());
    
    	return userDto;
    }
}