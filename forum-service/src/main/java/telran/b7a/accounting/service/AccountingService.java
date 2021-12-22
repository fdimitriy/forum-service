package telran.b7a.accounting.service;

import telran.b7a.accounting.dto.LoginUserDto;
import telran.b7a.accounting.dto.RegisterUserDto;
import telran.b7a.accounting.dto.ResponseRoleUserDto;
import telran.b7a.accounting.dto.ResponseUserDto;
import telran.b7a.accounting.dto.UpdateUserDto;

public interface AccountingService {
	
	ResponseUserDto registerUser(RegisterUserDto registerUserDto);
	
	ResponseUserDto loginUser(String login);
//	ResponseUserDto loginUser(LoginUserDto loginUserDto);
	
	ResponseUserDto deleteUser(String login);
	
	ResponseUserDto updateUser(String login, UpdateUserDto updateUserDto);
	
	ResponseRoleUserDto addRole(String login, String role);
	
	ResponseRoleUserDto deleteRole(String login, String role);
	
	void changePassword(LoginUserDto loginUserDto);

}
