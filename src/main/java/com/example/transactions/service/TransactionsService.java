package com.example.transactions.service;

import com.example.transactions.entity.Transactions;
import com.example.transactions.repository.TransactionsRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

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
}
