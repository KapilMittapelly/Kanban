package kanban.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import kanban.validation.PasswordMatches;
import kanban.validation.ValidEmail;
import kanban.validation.ValidPassword;

@PasswordMatches
public class UserDto {
	@NotNull
	@Size(min = 1, message="Name cannot be empty")
	private String name;

	@ValidPassword(message="Invalid password")
	private String password;

	@NotNull
	@Size(min = 1, message="Password dont match")
	private String matchingPassword;

	@ValidEmail
	@NotNull
	private String email;
	
	private int userId;

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(final String email) {
		this.email = email;
	}

	private String role;

	public String getRole() {
		return role;
	}

	public void setRole(final String role) {
		this.role = role;
	}

	public String getName() {
		return name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(final String password) {
		this.password = password;
	}

	public String getMatchingPassword() {
		return matchingPassword;
	}

	public void setMatchingPassword(final String matchingPassword) {
		this.matchingPassword = matchingPassword;
	}

	@Override
	public String toString() {
		return "UserDto [name=" + name + ", password=" + password + ", matchingPassword=" + matchingPassword
				+ ", email=" + email + ", userId=" + userId + ", role=" + role + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((matchingPassword == null) ? 0 : matchingPassword.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((password == null) ? 0 : password.hashCode());
		result = prime * result + ((role == null) ? 0 : role.hashCode());
		result = prime * result + userId;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UserDto other = (UserDto) obj;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (matchingPassword == null) {
			if (other.matchingPassword != null)
				return false;
		} else if (!matchingPassword.equals(other.matchingPassword))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
			return false;
		if (role == null) {
			if (other.role != null)
				return false;
		} else if (!role.equals(other.role))
			return false;
		if (userId != other.userId)
			return false;
		return true;
	}
}
