package telran.b7a.accounting.model;

import java.util.HashSet;
import java.util.Set;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode( of = {"login" })
@Document( collection = "users") // с таким именем коллекция сохранится в БД MongoDB
@NoArgsConstructor
public class UserAccount {
	@Id
	String login;
	String password;
	String firstName;
	String lastName;
	Set<String> roles = new HashSet<String>();
	
	public UserAccount(String login, String password, String firstName, String lastName) {
		super();
		this.login = login;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
	}
	
	
	public boolean addRole(String role) {
		return roles.add(role);
	}
	
	public boolean removeRole(String role) {
		return roles.remove(role);
	}

	

}
