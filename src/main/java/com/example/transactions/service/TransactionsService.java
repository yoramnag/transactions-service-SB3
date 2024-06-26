package com.example.transactions.service;

import com.example.transactions.dto.BlackListDto;
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

    private static final int MAX_TRANSACTION_PER_A_DAY = 5;
    private static final double MAX_AMOUNT_PER_A_DAY = 3000;


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
        transaction.setMaskCreditCard(Utils.mask(transaction.getCreditCard()));
        transaction.setCreditCard(Utils.maskCreditCard(transaction.getCreditCard()));
        transactionsRepository.save(transaction);
    }

    private void checkForFraud(Transactions transaction) {
        checkForBlackList(transaction.getCreditCard());
        List<Transactions> transactionsToday = findByCreditCardAndDate(transaction);
        checkTransactionsPerADay(transactionsToday,transaction);
        checkAmountPerADay(transaction, transactionsToday);
    }

    private void checkAmountPerADay(Transactions transaction, List<Transactions> transactionsToday) {
        double sum = 0;
        if(transaction.getAmount() >= MAX_AMOUNT_PER_A_DAY) {
            addCardToBlalList(transaction.getCreditCard());
            throw new FraudException("transactions is not valid , card passed his max amount per a day  ");
        }
        if(transactionsToday.size() > 0) {
            for (int i = 0; i < transactionsToday.size(); i++) {
                sum = sum + transactionsToday.get(i).getAmount();
            }
            if (sum >= MAX_AMOUNT_PER_A_DAY) {
                addCardToBlalList(transaction.getCreditCard());
                throw new FraudException("transactions is not valid , card passed his max amount per a day  ");
            }
        }
    }

    private void checkTransactionsPerADay(List<Transactions> transactionsToday, Transactions transaction) {
        if(transactionsToday.size()+1 > MAX_TRANSACTION_PER_A_DAY) {
            addCardToBlalList(transaction.getCreditCard());
            throw new FraudException("transactions is not valid , card passed his max transactions per a day  ");
        }
    }

    private void addCardToBlalList(String creditCardNumber) {
        BlackListDto blackListDto = new BlackListDto();
        blackListDto.setCreditCard(creditCardNumber);
        blackListProxy.createBlackListCard(blackListDto);
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
