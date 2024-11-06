package com.example.transactions.service;

import com.example.transactions.dto.BlackListDto;
import com.example.transactions.dto.TransactionsInfoDto;
import com.example.transactions.entity.Transactions;
import com.example.transactions.exception.FraudException;
import com.example.transactions.exception.LuhnException;
import com.example.transactions.exception.TransactionNotFoundException;
import com.example.transactions.repository.TransactionsRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class TransactionsService {

    private TransactionsRepository transactionsRepository;

    BlackListProxy blackListProxy;

//    @Autowired
//    private TransactionsInfoDto transactionsInfoDto;

    /**
     * get all records from transactions table
     * @return  list of all transactions from transactions table
     */
    public List<Transactions> findAll(){
        return transactionsRepository.findAll();
    }

    /**
     * get transaction by transactionId
     * @param transactionId to get
     * @return transaction
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
     *
     * @param transaction         to add
     * @param transactionsInfoDto
     * @param correlationId
     */
    public void saveTransaction(Transactions transaction, TransactionsInfoDto transactionsInfoDto, String correlationId){
        checkLuhnValidetor(transaction);
        checkForFraud(transaction,transactionsInfoDto,correlationId);
        transaction.setMaskCreditCard(Utils.mask(transaction.getCreditCard()));
        transaction.setCreditCard(Utils.maskCreditCard(transaction.getCreditCard()));
        transactionsRepository.save(transaction);
    }

    /**
     * check for fraud
     *
     * @param transaction         to check
     * @param transactionsInfoDto
     * @param correlationId
     */
    private void checkForFraud(Transactions transaction, TransactionsInfoDto transactionsInfoDto, String correlationId) {
        checkForBlackList(transaction.getCreditCard(),correlationId);
        List<Transactions> transactionsToday = findByCreditCardAndDate(transaction);
        checkTransactionsPerADay(transactionsToday,transaction,transactionsInfoDto,correlationId);
        checkAmountPerADay(transaction, transactionsToday,transactionsInfoDto,correlationId);
    }

    /**
     * check if the transaction didn't pass the max amount allowed per a day
     *
     * @param transaction         to be checked
     * @param transactionsToday   a list aof all transactions for a specific day
     * @param transactionsInfoDto
     * @param correlationId
     */
    private void checkAmountPerADay(Transactions transaction, List<Transactions> transactionsToday, TransactionsInfoDto transactionsInfoDto, String correlationId) {
        double sum = 0;
        if(transaction.getAmount() >= transactionsInfoDto.getMaxAmountPerADay()) {
            addCardToBlackList(transaction.getCreditCard(), correlationId);
            throw new FraudException("transactions is not valid , card passed his max amount per a day  ");
        }
        if(!transactionsToday.isEmpty()) {
            for (int i = 0; i < transactionsToday.size(); i++) {
                sum = sum + transactionsToday.get(i).getAmount();
            }
            sum = sum + transaction.getAmount();
            if (sum >= transactionsInfoDto.getMaxAmountPerADay()) {
                addCardToBlackList(transaction.getCreditCard(), correlationId);
                throw new FraudException("transactions is not valid , card passed his max amount per a day  ");
            }
        }
    }

    /**
     * sum the number of transactions and check if it passed the maxTrsnsactionsPerADAy
     *
     * @param transactionsToday   a list aof all transactions for a specific day
     * @param transaction         to be checked
     * @param transactionsInfoDto
     * @param correlationId
     */
    private void checkTransactionsPerADay(List<Transactions> transactionsToday, Transactions transaction, TransactionsInfoDto transactionsInfoDto, String correlationId) {
        if(transactionsToday.size()+1 > transactionsInfoDto.getMaxTrsnsactionsPerADAy()) {
            addCardToBlackList(transaction.getCreditCard(),correlationId);
            throw new FraudException("transactions is not valid , card passed his max transactions per a day");
        }
    }

    /**
     * add credit card to black list table
     *
     * @param creditCardNumber to add to black list
     * @param correlationId
     */
    private void addCardToBlackList(String creditCardNumber, String correlationId) {
        BlackListDto blackListDto = new BlackListDto();
        blackListDto.setCreditCard(creditCardNumber);
        blackListProxy.createBlackListCard(correlationId,blackListDto);
    }

    /**
     * get  all transactions for a credit card for a specific day
     * @param transaction to be checked
     * @return a list of all transitions
     */
    private List<Transactions> findByCreditCardAndDate(Transactions transaction) {
        String creditCardNumber = Utils.maskCreditCard(transaction.getCreditCard());
        return transactionsRepository.findByCreditCardAndDate(creditCardNumber,LocalDate.now());
    }

    /**
     * check if the credit card number is in the black list DB
     *
     * @param creditCard    credit card number
     * @param correlationId
     */
    private void checkForBlackList(String creditCard, String correlationId) {
        if(blackListProxy.checkIfCreditCradAllReadyExist(correlationId,creditCard)){
            throw new FraudException("transactions is not valid , card was found in black list !!!");
        }
    }

    /**
     * check if the credit card number is validate by Luha algorithm
     * @param transaction to be checked
     */
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
