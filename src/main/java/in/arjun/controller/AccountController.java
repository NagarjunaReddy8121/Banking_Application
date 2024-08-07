package in.arjun.controller;

import in.arjun.dto.Request;
import in.arjun.dto.Response;
import in.arjun.entity.Account;
import in.arjun.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/account")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @PostMapping("/create")
    public ResponseEntity<Response> saveAccount(@RequestBody Request request){
        Response account = accountService.createAccount(request);
        return new ResponseEntity<>(account, HttpStatus.CREATED);
    }

    @GetMapping("/find/{id}")
    public Account findAccountById(@PathVariable("id") Integer id){
        Optional<Account> accountById = accountService.getAccountById(id);
        if(accountById.isPresent()){
            return accountById.get();
        }else throw new RuntimeException("account not found");
    }

    @PostMapping("/{id}/deposit")
    public ResponseEntity<Account> deposit(@PathVariable("id") Integer id, @RequestBody Map<String,Double> request){
        Double amount = request.get("amount");
        return new ResponseEntity<>(accountService.deposit(id,amount),HttpStatus.CREATED);
    }

    @PostMapping("/{id}/withdraw")
    public ResponseEntity<Account> withdraw(@PathVariable("id") Integer id, @RequestBody Map<String,Double> req){
        Double amount = req.get("amount");
        return new ResponseEntity<>(accountService.withdraw(id,amount),HttpStatus.CREATED);
    }
}
