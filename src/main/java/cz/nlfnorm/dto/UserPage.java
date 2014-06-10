package cz.nlfnorm.dto;

import java.util.List;

import cz.nlfnorm.entities.User;

public class UserPage extends PageDto {
	
	private List<User> users;

	
	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}
	
	
}
