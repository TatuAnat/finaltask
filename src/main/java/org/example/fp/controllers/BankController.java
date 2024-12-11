package org.example.fp.controllers;

import org.example.fp.entity.Account;
import org.example.fp.repository.AccountRepository;
import org.example.fp.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController

public class BankController {



    @Autowired
    private AccountService accountService;



    @GetMapping("/api/getBalance/{userId}")
    public ResponseEntity<Map<String, Object>> getBalance(@PathVariable int userId) {
        //return "call getBalance() with " + userId;
        Map<String, Object> response = new HashMap<>();

        try {
            Double balance = accountService.getBalance(userId);
            response.put("balance", balance);
        } catch (Exception e) {
            response.put("balance", -1.0);
            response.put("error", e.getMessage());
        }

        return ResponseEntity.ok(response);
    }

    @GetMapping("/api/takeMoney/{userId}/{amount}")
    public ResponseEntity<Map<String, Object>> takeMoney(@PathVariable int userId, @PathVariable double amount) {
        Map<String, Object> response = new HashMap<>();

       try {
          accountService.takeMoney(userId, amount);
           response.put("result", 1);
       }catch (Exception e) {
           response.put("result", 0);
           response.put("error", e.getMessage());
       }

        return ResponseEntity.ok(response);
    }

    @GetMapping("/api/putMoney/{userId}/{amount}")
    public ResponseEntity<Map<String, Object>> putMoney(@PathVariable int userId, @PathVariable double amount) {
        Map<String, Object> response = new HashMap<>();
        try {
            accountService.putMoney(userId, amount);
            response.put("result", 1);
        }catch (Exception e) {
            response.put("result", 0);
            response.put("error", e.getMessage());
        }



        return  ResponseEntity.ok(response);
    }


}
