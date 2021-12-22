package telran.b7a.accounting.controller;

import java.security.Principal;
import java.util.Base64;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import telran.b7a.accounting.dto.LoginUserDto;
import telran.b7a.accounting.dto.RegisterUserDto;
import telran.b7a.accounting.dto.ResponseRoleUserDto;
import telran.b7a.accounting.dto.ResponseUserDto;
import telran.b7a.accounting.dto.UpdateUserDto;
import telran.b7a.accounting.service.AccountingService;

@RestController
@RequestMapping("/account")
public class AccountingController {
	
	AccountingService accountingService;	
	
	@Autowired
	public AccountingController(AccountingService accountingService) {
		super();
		this.accountingService = accountingService;
	}
	
	@PostMapping("/register")
	public ResponseUserDto register(@RequestBody RegisterUserDto registerUserDto) {
		return accountingService.registerUser(registerUserDto);
	}
	
	@PostMapping("/login")
	public ResponseUserDto login(Principal principal) {
		return accountingService.loginUser(principal.getName());
	}	
	
//	@PostMapping("/login")
//	public ResponseUserDto login(@RequestHeader ("Authorization") String token) {
//      //String[] arr = token.split(" ");	
		
//		token = token.split(" ")[1]; // берем первый элемент из масссива после метода сплит
//		byte[] bytesDecode = Base64.getDecoder().decode(token);	// метод после декодирования возвращает массив байтов	
//		token = new String(bytesDecode);
//		String[] credentials = token.split(":");
//		LoginUserDto loginUserDto = new LoginUserDto(credentials[0], credentials[1]);		
		
//		System.out.println(credentials[0] + " - Login");		// для себя смотрю имя пользователя
//		System.out.println(credentials[1] + " - Password");     // для себя смотрю пароль пользователя
		
//		return accountingService.loginUser(credentials[0]);
//	}
	
	
	@DeleteMapping("/user/{login}")
	public ResponseUserDto delete(@PathVariable String login) {
		return accountingService.deleteUser(login);
	}
	
	@PutMapping("/user/{login}")
	public ResponseUserDto update(@PathVariable String login, @RequestBody UpdateUserDto updateUserDto) {
		return accountingService.updateUser(login, updateUserDto);
	}
	
	@PutMapping("/user/{login}/role/{role}")
	public ResponseRoleUserDto addRole(@PathVariable String login, @PathVariable String role) {
		return accountingService.addRole(login, role);
	}
	
	@DeleteMapping("/user/{login}/role/{role}")
	public ResponseRoleUserDto deleteRole(@PathVariable String login, @PathVariable String role) {
		return accountingService.deleteRole(login, role);
	}
	
	@PutMapping("/password")
	public void changePassword(Principal principal, @RequestHeader("X-Password") String password) {
		accountingService.changePassword(new LoginUserDto(principal.getName(), password));
	}		
	
//	@PutMapping("/password")
//	public void changePassword(@RequestBody LoginUserDto loginUserDto) {
//		accountingService.changePassword(loginUserDto);
//	}	

}
