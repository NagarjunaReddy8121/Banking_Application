package in.arjun.service;


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
        Account account = getAccountById(id).orElseThrow(() -> new RuntimeException("account not found"));
        account.setBalance(account.getBalance()+amount);
        return repository.save(account);
    }

    @Override
    public Account withdraw(Integer id, Double amount) {
        Account account = getAccountById(id).orElseThrow(() -> new RuntimeException("account not found"));
         if(account.getBalance()<amount){
             throw new RuntimeException("insufficient funds");
         }
         account.setBalance(account.getBalance()-amount);
        return repository.save(account);
    }
}
