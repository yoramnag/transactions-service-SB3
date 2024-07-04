package com.example.transactions.entity;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
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
@Schema(
		name = "Transactions",
		description = "Schema to hold transactions INFO"
)
public class Transactions {

	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	@Column(name="transaction_Id")
	@Schema(
			description = "transaction ID",
			example = "1"
	)
	private int transactionId;
	
	@Column(name="credit_card")
	@NotEmpty(message = "credit card can NOT be null or empty")
	@Schema(
			description = "credit card number",
			example = "56e7fc920e4283f65412b1668110f5bf9552a697b90928869219158d87b70be7"
	)
	private String creditCard;
	
	@Column(name="amount")
	@DecimalMin(value = "1.0", message = "amount should be greater than 1")
	@Schema(
			description = "amount of the transaction",
			example = "1.0"
	)
	private double amount ;

	@CreatedDate
	@Schema(
			description = "current date and time for the transaction",
			example = "2024-07-04 19:57:24.513132"
	)
	private LocalDateTime date;
	
	@Column(name="mask_credit_card")
	@Schema(
			description = "mask credit card number",
			example = "XXXXXXXXXXXX0265"
	)
	private String maskCreditCard;

}
