package in.arjun.service;

import in.arjun.dto.Request;
import in.arjun.dto.Response;
import in.arjun.entity.Account;

import java.util.Optional;


public interface AccountService {

     Response createAccount(Request request);

     Optional<Account> getAccountById(Integer id);

     Account deposit(Integer id, Double amount);

     Account withdraw(Integer id,Double amount);


}
