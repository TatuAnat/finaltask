package org.example.fp.controllers;

import org.example.fp.entity.Account;
import org.example.fp.entity.Operation;
import org.example.fp.repository.AccountRepository;
import org.example.fp.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
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
        } catch (Exception e) {
            response.put("result", 0);
            response.put("error", e.getMessage());
        }

        return ResponseEntity.ok(response);
    }

    //Primer':
    //http://localhost:8080/api/getOperationList/123456?from=2024-01-01T00:00:00&to=2024-12-31T00:00:00
    //http://localhost:8080/api/getOperationList/123456?from=2024-01-01T00:00:00
    //http://localhost:8080/api/getOperationList/123456?to=2024-12-31T00:00:00

    @GetMapping("/api/getOperationList/{userId}")
    public ResponseEntity<Map<String, Object>> getOperationList(@PathVariable int userId, @RequestParam(required = false) String from, @RequestParam(required = false) String to) {

        //2024-12-28
        Map<String, Object> response = new HashMap<>();

        try{
            // String -> LocalDateTime
            DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;

            LocalDateTime fromDateTime = null;
            LocalDateTime toDateTime = null;
            if(from != null){
                //fromDateTime = LocalDateTime.parse(from, formatter);
                fromDateTime = LocalDateTime.parse(from, formatter);
            }

            if(to != null){
                toDateTime = LocalDateTime.parse(to, formatter);
            }

            List<Operation> operations =  accountService.getOperationList(userId, fromDateTime,toDateTime);

            response.put("operations", operations);
        } catch (Exception e) {
            response.put("operations", null);
            response.put("error", e.getMessage());
            return ResponseEntity.ok(response);
        }

        return ResponseEntity.ok(response);
    }

}
