package com.example.transactions.service;

import com.example.transactions.entity.Transactions;
import com.example.transactions.exception.FraudException;
import com.example.transactions.exception.LuhnException;
import com.example.transactions.exception.TransactionNotFoundException;
import com.example.transactions.repository.TransactionsRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class TransactionsService {

    private TransactionsRepository transactionsRepository;

    BlackListProxy blackListProxy;

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

    /**
     * add new transaction to transactions table
     * @param transaction to add
     */
    public void saveTransaction(Transactions transaction){
        checkLuhnValidetor(transaction);
        transaction.setDate(LocalDateTime.now());
        checkForFraud(transaction);
    }

    private void checkForFraud(Transactions transaction) {
        checkForBlackList(transaction.getCreditCard());
        List<Transactions> transactionsToday = findByCreditCardAndDate(transaction);
        System.out.println(transactionsToday);
    }

    private List<Transactions> findByCreditCardAndDate(Transactions transaction) {
        String creditCardNumber = Utils.maskCreditCard(transaction.getCreditCard());
        return transactionsRepository.findByCreditCardAndDate(creditCardNumber, transaction.getDate());
    }

    private void checkForBlackList(String creditCard) {
        if(blackListProxy.checkIfCreditCradAllReadyExist(creditCard)){
            throw new FraudException("transactions is not valid , card was found in black list !!!");
        }
    }

    private void checkLuhnValidetor(Transactions transaction) {
        if (!Utils.luhnValidetor(transaction.getCreditCard())) {
            throw new LuhnException(Utils.mask(transaction.getCreditCard()) + " is not valid");
        }
    }


    /**
     * delete transaction by transactionId
     * @param transactionId to delete from the Transactions table
     */
    public void deleteTransactionByTransactionId(int transactionId){
        Optional<Transactions> transaction = transactionsRepository.findById(transactionId);
        if(!transaction.isPresent()){
            throw new TransactionNotFoundException("transaction", "transaction ID" , String.valueOf(transactionId));
        }
        transactionsRepository.deleteById(transactionId);
    }
}
