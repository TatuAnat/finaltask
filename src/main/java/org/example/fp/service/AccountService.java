package org.example.fp.service;

import org.example.fp.entity.Account;
import org.example.fp.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    public Double getBalance(Integer userId) throws Exception {

        Account account = accountRepository.findByUserId(userId);
        if(account == null){
            throw new Exception("Pol'sovatel ne naiden " + userId);
        }

        return account.getBalance();
    }


    public Account takeMoney(Integer userId, Double balance) throws Exception {
        Account account = accountRepository.findByUserId(userId);
        if(account == null){
            throw new Exception("Pol'sovatel ne naiden " + userId);
        }

        Double newBalance =account.getBalance() - balance;

        if(newBalance < 0){
            throw new Exception("nedostatochno sredstv");
        }

        account.setBalance(newBalance);
        return accountRepository.save(account);
    }

    public Account putMoney(Integer userId, Double balance) throws Exception {
        Account account = accountRepository.findByUserId(userId);
        if(account == null){
            throw new Exception("Pol'sovatel ne naiden " + userId);
        }
        account.setBalance(account.getBalance() + balance);
        return accountRepository.save(account);
    }
}
