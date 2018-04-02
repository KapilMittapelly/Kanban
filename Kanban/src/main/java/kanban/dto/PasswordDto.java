package kanban.dto;

import kanban.validation.PasswordMatches;
import kanban.validation.ValidPassword;

@PasswordMatches
public class PasswordDto {

	@ValidPassword
    private String password;

    private String matchingPassword;

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getMatchingPassword() {
		return matchingPassword;
	}

	public void setMatchingPassword(String matchingPassword) {
		this.matchingPassword = matchingPassword;
	}

	@Override
	public String toString() {
		return "PasswordDto [password=" + password + ", matchingPassword=" + matchingPassword + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((matchingPassword == null) ? 0 : matchingPassword.hashCode());
		result = prime * result + ((password == null) ? 0 : password.hashCode());
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
		PasswordDto other = (PasswordDto) obj;
		if (matchingPassword == null) {
			if (other.matchingPassword != null)
				return false;
		} else if (!matchingPassword.equals(other.matchingPassword))
			return false;
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
			return false;
		return true;
	}
}
