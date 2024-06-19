package com.example.transactions.repository;

import com.example.transactions.entity.Transactions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;


@Repository
public interface TransactionsRepository extends JpaRepository<Transactions, Integer>{
	
	List<Transactions> findByCreditCard(String creditCardNumber);
	
	List<Transactions> findByDate(LocalDateTime date);
	
	List<Transactions> findByCreditCardAndDate(String creditCardNumber,LocalDateTime date);

}
