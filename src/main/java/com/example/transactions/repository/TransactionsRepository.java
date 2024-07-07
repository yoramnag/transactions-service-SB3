package com.example.transactions.repository;

import com.example.transactions.entity.Transactions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;


@Repository
public interface TransactionsRepository extends JpaRepository<Transactions, Integer>{
	
	List<Transactions> findByCreditCardAndDate(String creditCardNumber, LocalDate date);

}
