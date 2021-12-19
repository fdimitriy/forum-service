package telran.b7a.accounting.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import telran.b7a.accounting.dao.AccountingRepository;
import telran.b7a.accounting.dto.LoginUserDto;
import telran.b7a.accounting.dto.RegisterUserDto;
import telran.b7a.accounting.dto.ResponseRoleUserDto;
import telran.b7a.accounting.dto.ResponseUserDto;
import telran.b7a.accounting.dto.UpdateUserDto;
import telran.b7a.accounting.dto.exception.UserExistsException;
import telran.b7a.accounting.dto.exception.UserNotFoundException;
import telran.b7a.accounting.model.UserAccount;

@Service
public class AccountingServiceImpl implements AccountingService {
	
	AccountingRepository accountingRepository;
	ModelMapper modelMapper;
	
	@Autowired
	public AccountingServiceImpl(AccountingRepository accountingRepository, ModelMapper modelMapper) {
		super();
		this.accountingRepository = accountingRepository;
		this.modelMapper = modelMapper;
	}

	@Override
	public ResponseUserDto registerUser(RegisterUserDto registerUserDto) {
		if (accountingRepository.existsById(registerUserDto.getLogin())) {
			throw new UserExistsException(registerUserDto.getLogin());
		}
		UserAccount userAccount = modelMapper.map(registerUserDto, UserAccount.class);
		userAccount.addrole("user");
		accountingRepository.save(userAccount);
		return modelMapper.map(userAccount, ResponseUserDto.class);
	}

	@Override
	public ResponseUserDto loginUser(LoginUserDto loginUserDto) {
		UserAccount userAccount = accountingRepository.findById(loginUserDto.getLogin())
				                                         .orElseThrow(() -> new UserNotFoundException(loginUserDto.getLogin()));
		return modelMapper.map(userAccount, ResponseUserDto.class);
	}

	@Override
	public ResponseUserDto deleteUser(String login) {
		UserAccount userAccount = accountingRepository.findById(login)
				                                         .orElseThrow(() -> new UserNotFoundException(login)); 
		accountingRepository.delete(userAccount);
		return modelMapper.map(userAccount, ResponseUserDto.class);
	}

	@Override
	public ResponseUserDto updateUser(String login, UpdateUserDto updateUserDto) {
		UserAccount userAccount = accountingRepository.findById(login)
                                                         .orElseThrow(() -> new UserNotFoundException(login)); 
		String firstName = updateUserDto.getFirstName();
		if( firstName != null) {
			userAccount.setFirstName(firstName);
		}
		String lastName = updateUserDto.getLastName();
		if( lastName != null) {
			userAccount.setLastName(lastName);
		}
		accountingRepository.save(userAccount);		
		return modelMapper.map(userAccount, ResponseUserDto.class);
	}

	@Override
	public ResponseRoleUserDto addRole(String login, String role) {
		UserAccount userAccount = accountingRepository.findById(login)
                                                         .orElseThrow(() -> new UserNotFoundException(login)); 
		if ( role != null) {
		     userAccount.addrole(role);
		}
		accountingRepository.save(userAccount);
		return modelMapper.map(userAccount, ResponseRoleUserDto.class);
	}

	@Override
	public ResponseRoleUserDto deleteRole(String login, String role) {
		UserAccount userAccount = accountingRepository.findById(login)
                                                         .orElseThrow(() -> new UserNotFoundException(login)); 
		if ( role != null) {
		     userAccount.removeRole(role);
		}
		accountingRepository.save(userAccount);
		return modelMapper.map(userAccount, ResponseRoleUserDto.class);
	}

	@Override
	public void changePassword(LoginUserDto loginUserDto) {
		UserAccount userAccount = accountingRepository.findById(loginUserDto.getLogin())
                                                         .orElseThrow(() -> new UserNotFoundException(loginUserDto.getLogin()));
		if ( loginUserDto.getPassword() != null) {
		     userAccount.setPassword(loginUserDto.getPassword());
		}
		accountingRepository.save(userAccount);
	}	

}
