package fr.excilys.databasecomputer.controller;

import java.util.Objects;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fr.excilys.databasecomputer.configuration.JwtTokenUtil;
import fr.excilys.databasecomputer.dtos.UserDTO;
import fr.excilys.databasecomputer.exception.AuthenticationException;
import fr.excilys.databasecomputer.service.UsersService;

@RestController
@RequestMapping("/login")
public class LoginController {

	private JwtTokenUtil jwtTokenUtil;
	private UsersService usersService;
	private AuthenticationManager authenticationManager;

	public LoginController(UsersService usersService, AuthenticationManager authenticationManager,
			JwtTokenUtil jwtTokenUtil) {
		this.usersService = usersService;
		this.authenticationManager = authenticationManager;
		this.jwtTokenUtil = jwtTokenUtil;
	}

	@PostMapping
	public ResponseEntity<?> login(@RequestBody UserDTO authentificationRequest) throws AuthenticationException {
		authenticate(authentificationRequest.getUsername(), authentificationRequest.getPassword());
		final UserDetails userDetails = usersService.loadUserByUsername(authentificationRequest.getUsername());
		final String token = jwtTokenUtil.generateToken(userDetails);
		return ResponseEntity.ok(token);
	}

	@ExceptionHandler({ AuthenticationException.class })
	public ResponseEntity<String> handleAuthenticationException(AuthenticationException e) {
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
	}

	private void authenticate(String username, String password) {
		Objects.requireNonNull(username);
		Objects.requireNonNull(password);

		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
		} catch (DisabledException e) {
			throw new AuthenticationException("USER_DISABLED", e);
		} catch (BadCredentialsException e) {
			throw new AuthenticationException("INVALID_CREDENTIALS", e);
		}
	}

}
