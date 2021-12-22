package telran.b7a.security;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Component;

@Component
public class SecurityContextImpl implements SecurityContext {
	Map<String, UserProfile> context = new ConcurrentHashMap<String, UserProfile>();

	@Override
	public boolean addUser(UserProfile user) {		
		return context.putIfAbsent(user.getLogin(), user) == null;
	}

	@Override
	public UserProfile removeUser(String login) {
		return context.remove(login);
	}

	@Override
	public UserProfile getUser(String login) {
		return context.get(login);
	}

}
