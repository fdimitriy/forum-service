package telran.b7a.security;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Service;

import telran.b7a.accounting.model.UserAccount;

@Service
public class SessionServiceImpl implements SessionService {
	
	Map<String, UserAccount> users = new ConcurrentHashMap<String, UserAccount>();

	@Override
	public UserAccount addUser(String sessionId, UserAccount user) {
		return users.put(sessionId, user);
	}

	@Override
	public UserAccount getUser(String sessionId) {
		return users.get(sessionId);
	}

	@Override
	public UserAccount removeUser(String sessionId) {
		return users.remove(sessionId);
	}

}
