package telran.b7a.security;

import telran.b7a.accounting.model.UserAccount;

public interface SessionService {
	
	UserAccount addUser(String sessionId, UserAccount user);
	
	UserAccount getUser(String sessionId);
	
	UserAccount removeUser(String sessionId);

}
