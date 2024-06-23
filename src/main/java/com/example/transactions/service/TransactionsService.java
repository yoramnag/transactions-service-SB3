package com.example.transactions.service;

import com.example.transactions.entity.Transactions;
import com.example.transactions.exception.TransactionNotFoundException;
import com.example.transactions.repository.TransactionsRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class TransactionsService {

    private TransactionsRepository transactionsRepository;

    /**
     * get all records from transactions table
     * @return  list of all transactions from transactions table
     */
    public List<Transactions> findAll(){
        return transactionsRepository.findAll();
    }

    /**
     * @param transactionId
     * @return
     */
    public Optional<Transactions> findTransactionByTransactionId(int transactionId){
        Optional<Transactions> transaction = transactionsRepository.findById(transactionId);
        if(!transaction.isPresent()){
            throw new TransactionNotFoundException("transaction", "transaction ID" , String.valueOf(transactionId));
        }
        return transaction;
    }

    public void deleteTransactionByTransactionId(int transactionId){
        Optional<Transactions> transaction = transactionsRepository.findById(transactionId);
        if(!transaction.isPresent()){
            throw new TransactionNotFoundException("transaction", "transaction ID" , String.valueOf(transactionId));
        }
        transactionsRepository.deleteById(transactionId);
    }
}
