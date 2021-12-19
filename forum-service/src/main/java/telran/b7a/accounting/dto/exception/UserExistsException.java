package telran.b7a.accounting.dto.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import lombok.NoArgsConstructor;

@NoArgsConstructor
@ResponseStatus( code = HttpStatus.CONFLICT)
public class UserExistsException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5662083505371630148L;
	
	public UserExistsException(String login) {
		super("User with login '" + login + "' exists");				
	}

}
