package com.example.transactions.entity;


import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@EntityListeners(AuditingEntityListener.class)
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

	@CreatedDate
	private LocalDateTime date;
	
	@Column(name="mask_credit_card")
	private String maskCreditCard;

}
