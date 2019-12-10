package fr.excilys.databasecomputer.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import fr.excilys.databasecomputer.dao.implement.UsersDAO;
import fr.excilys.databasecomputer.entity.Users;
import fr.excilys.databasecomputer.exception.SQLExceptionComputerNotFound;

@Service
public class UsersService implements UserDetailsService {

	private UsersDAO userDAO;

	public UsersService(UsersDAO userDAO) {
		this.userDAO = userDAO;
	}

	public Users findUser(String name) throws SQLExceptionComputerNotFound {
		return userDAO.findUser(name);
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Users user = userDAO.findUser(username);
		if (user == null) {
			throw new UsernameNotFoundException("UserName " + username + " not found");
		} else {
			return user;
		}
	}
}
