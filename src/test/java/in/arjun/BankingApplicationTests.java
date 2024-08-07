package in.arjun;


import in.arjun.advice.AccountNotFoundException;
import in.arjun.advice.InsufficientFundsException;
import in.arjun.entity.Account;
import in.arjun.repository.AccountRepository;
import in.arjun.service.AccountService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
class BankingApplicationTests {

	@Autowired
	private AccountService accountService;

	@MockBean
	private AccountRepository accountRepository;


	private Account account;

	@ParameterizedTest
	@ValueSource(ints = {1, 2, 3})
	public void getAccountByIdTest(Integer id){
		when(accountRepository.findById(id))
				.thenReturn(Optional.of(new Account(1, "arjun", "ajs2829sbxjsx", 1000.00)));
	}
	@Test
	public void createAccountTest(){
		Account account=new Account(10,"akil","kbsuh73r829",1200.00);
		when(accountRepository.save(account)).thenReturn(account);
	}

	@BeforeEach
	public void setUp(){
		account=new Account();
		account.setId(1);
		account.setBalance(100.00);
	}

	@Test
	public void checkDepositAmountIncreaseOrNot(){
		when(accountRepository.findById(1)).thenReturn(Optional.of(account));
		when(accountRepository.save(any(Account.class))).thenReturn(account);

		Account deposit = accountService.deposit(1, 50.00);

		assertEquals(150.0,deposit.getBalance());
		verify(accountRepository,times(1)).findById(1);
		verify(accountRepository,times(1)).save(account);
	}

	@Test
	public void checkDepositShouldThrowExceptionOrNot(){
		when(accountRepository.findById(1)).thenReturn(Optional.empty());

		assertThrows(AccountNotFoundException.class,()->accountService.deposit(1,50.00));
		verify(accountRepository,times(1)).findById(1);
		verify(accountRepository,times(0)).save(any(Account.class));
	}

	@Test
	public void checkWithdrawAmountDecreasedOrNot(){
		when(accountRepository.findById(1)).thenReturn(Optional.of(account));
		when(accountRepository.save(any(Account.class))).thenReturn(account);

		Account withdraw = accountService.withdraw(1, 30.00);

		assertEquals(70.00,withdraw.getBalance());
		verify(accountRepository,times(1)).findById(1);
		verify(accountRepository,times(1)).save(account);
	}

	@Test
	public void checkWithdrawShouldThrowExceptionOrNot(){
		when(accountRepository.findById(1)).thenReturn(Optional.empty());

		assertThrows(AccountNotFoundException.class,()->accountService.withdraw(1,30.00));
		verify(accountRepository,times(1)).findById(1);
		verify(accountRepository,times(0)).save(any(Account.class));
	}

	@Test
	public void checkWithdrawShouldThrownInsufficientFundsExceptionOrNot(){
		when(accountRepository.findById(1)).thenReturn(Optional.of(account));

		assertThrows(InsufficientFundsException.class,()->accountService.withdraw(1,200.00));
		verify(accountRepository,times(1)).findById(1);
		verify(accountRepository,times(0)).save(any(Account.class));
	}



}
