package in.arjun.service;


import in.arjun.advice.AccountNotFoundException;
import in.arjun.advice.InsufficientFundsException;
import in.arjun.dto.Request;
import in.arjun.dto.Response;
import in.arjun.entity.Account;
import in.arjun.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.Optional;
import java.util.UUID;

@Service
public class AccountServiceImpl implements AccountService{

    @Autowired
    private AccountRepository repository;

    @Override
    public Response createAccount(Request request) {
        Account account= new Account();
        account.setName(request.getName());
        account.setAccountNumber(UUID.randomUUID().toString());
        account.setBalance(request.getBalance());
       Account savedAccount= repository.save(account);
     return new Response(account);
    }

    @Override
    public Optional<Account> getAccountById(Integer id) {

        return  repository.findById(id);
    }

    @Override
    public Account deposit(Integer id, Double amount) {
        Account account = getAccountById(id).orElseThrow(() -> new AccountNotFoundException("account not found with the given id:"+id));
        account.setBalance(account.getBalance()+amount);
        return repository.save(account);
    }

    @Override
    public Account withdraw(Integer id, Double amount) {
        Account account = getAccountById(id).orElseThrow(() -> new AccountNotFoundException("account not found with the given id:"+id));
         if(account.getBalance()<amount){
             throw new InsufficientFundsException("insufficient funds");
         }
         account.setBalance(account.getBalance()-amount);
        return repository.save(account);
    }
}
