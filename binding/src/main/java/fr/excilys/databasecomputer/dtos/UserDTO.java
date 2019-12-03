package fr.excilys.databasecomputer.dtos;

public class UserDTO {

	private String username;
	private String password;

	private UserDTO() { }

	private UserDTO(String username, String password) {
		this.username = username;
		this.password = password;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
