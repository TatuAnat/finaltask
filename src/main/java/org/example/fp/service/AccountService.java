package org.example.fp.service;

import org.example.fp.entity.Account;
import org.example.fp.entity.Operation;
import org.example.fp.entity.Transfer;
import org.example.fp.enums.OperationType;
import org.example.fp.repository.AccountRepository;
import org.example.fp.repository.OperationRepository;
import org.example.fp.repository.TransferRepository;
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

    @Autowired
    private TransferRepository transferRepository;

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

    public Transfer transferMoney(Integer fromUserId, Integer toUserId, Double amount) throws Exception {
        Account fromUserAccount = accountRepository.findByUserId(fromUserId);
        Account toUserAccount = accountRepository.findByUserId(toUserId);

        if(fromUserAccount == null || toUserAccount == null){
            throw new Exception("Account ne naiden");
        }

        if(fromUserAccount.getBalance() < amount){
            throw new Exception("nedostatochno sredstv");
        }

        //Sosdan transfer
        Transfer transfer = new Transfer();
        transfer.setFromUserId(fromUserId);
        transfer.setToUserId(toUserId);
        transfer.setAmount(amount);
        transfer.setTimestamp(LocalDateTime.now());
        transferRepository.save(transfer);

        fromUserAccount.setBalance(fromUserAccount.getBalance() - amount);
        toUserAccount.setBalance(toUserAccount.getBalance() + amount);

        accountRepository.save(fromUserAccount);
        accountRepository.save(toUserAccount);


        Operation operationFromUser = new Operation();
        Operation operationToUser = new Operation();


        operationFromUser.setUserId(fromUserId);
        operationFromUser.setAmount(amount);
        operationFromUser.setType(OperationType.TAKE);
        operationFromUser.setTimestamp(LocalDateTime.now());
        operationRepository.save(operationFromUser);

        operationToUser.setUserId(toUserId);
        operationToUser.setAmount(amount);
        operationToUser.setType(OperationType.PUT);
        operationToUser.setTimestamp(LocalDateTime.now());
        operationRepository.save(operationToUser);

        return transfer;
    }
}
