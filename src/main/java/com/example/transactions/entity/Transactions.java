package com.example.transactions.entity;


import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Transactions {

	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	@Column(name="transaction_Id")
	private int transactionId;
	
	@Column(name="credit_card")
	private String creditCard;
	
	@Column(name="amount")
	@DecimalMin(value = "1.0", message = "amount should be greater than 1")
	private double amount ;

//	@Temporal(TemporalType.DATE)
	private LocalDateTime date;
	
	@Column(name="mask_credit_card")
	private String maskCreditCard;

}
