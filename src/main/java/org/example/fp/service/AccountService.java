package org.example.fp.service;

import org.example.fp.entity.Account;
import org.example.fp.entity.Operation;
import org.example.fp.enums.OperationType;
import org.example.fp.repository.AccountRepository;
import org.example.fp.repository.OperationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private OperationRepository operationRepository;

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

        Operation operation = new Operation();
        operation.setUserId(userId);
        operation.setAmount(balance);
        operation.setType(OperationType.TAKE);
        operation.setTimestamp(LocalDateTime.now());

        operationRepository.save(operation);

        return accountRepository.save(account);
    }

    public Account putMoney(Integer userId, Double balance) throws Exception {
        Account account = accountRepository.findByUserId(userId);
        if(account == null){
            throw new Exception("Pol'sovatel ne naiden " + userId);
        }

        account.setBalance(account.getBalance() + balance);

        Operation operation = new Operation();
        operation.setUserId(userId);
        operation.setAmount(balance);
        operation.setType(OperationType.PUT);
        operation.setTimestamp(LocalDateTime.now());

        operationRepository.save(operation);
        // UPDATE accounts SET ('ccc) values ('dqwdq) where userId = '1234567'
        return accountRepository.save(account);
    }

    //Sasha
    public List<Operation> getOperationList(Integer userId, LocalDateTime from, LocalDateTime to){
        if(from ==null && to ==null ){
            return operationRepository.findByUserId(userId);
        } else if (from == null) {
            return operationRepository.findByUserIdAndTimestampAfter(userId, to);
        } else if (to == null) {
            return operationRepository.findByUserIdAndTimestampBefore(userId, from);
        } else {
            return operationRepository.findByUserIdAndTimestampBetween(userId, from, to);
        }
    }
}
