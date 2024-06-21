package com.example.transactions.rest;

import com.example.transactions.entity.Transactions;
import com.example.transactions.service.TransactionsService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "/api" , produces = MediaType.APPLICATION_JSON_VALUE)
@AllArgsConstructor
public class TransactionsRestController {

    TransactionsService transactionsService;

    @GetMapping("/getAllTransactions")
    public ResponseEntity<List<Transactions>> getAllTransactions(){
        List<Transactions> transactionsList = transactionsService.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(transactionsList);
    }

    @GetMapping("/getTransaction")
    public ResponseEntity<Optional<Transactions>> getTransactionByTransactionId(@RequestParam int transactionId){
        Optional<Transactions> transaction = transactionsService.findTransactionByTransactionId(transactionId);
        return ResponseEntity.status(HttpStatus.OK).body(transaction);
    }
}
