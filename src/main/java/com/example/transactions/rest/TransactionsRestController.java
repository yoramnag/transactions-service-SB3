package com.example.transactions.rest;

import com.example.transactions.constants.TransactionsConstants;
import com.example.transactions.dto.ResponseDto;
import com.example.transactions.entity.Transactions;
import com.example.transactions.service.TransactionsService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/saveTransaction")
    public ResponseEntity<ResponseDto> createTransaction(@RequestBody Transactions transaction){
        transactionsService.saveTransaction(transaction);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ResponseDto(TransactionsConstants.STATUS_201,TransactionsConstants.MESSAGE_201));
    }

    @DeleteMapping("/deleteTransaction")
    public ResponseEntity<ResponseDto> deleteTransactionByTransactionId(@RequestParam int transactionId){
        transactionsService.deleteTransactionByTransactionId(transactionId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ResponseDto(TransactionsConstants.STATUS_200,TransactionsConstants.MESSAGE_200));
    }
}
