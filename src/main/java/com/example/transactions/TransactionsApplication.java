package com.example.transactions;

import com.example.transactions.dto.TransactionsInfoDto;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableFeignClients
@EnableJpaAuditing(auditorAwareRef = "auditAwareImpl")
@EnableConfigurationProperties(value = {TransactionsInfoDto.class})
@OpenAPIDefinition(
		info = @Info(
				title = "Transactions microservice REST API Documentation",
				description = "Transactions REST API for credit card microservice project",
				version = "v1",
				contact = @Contact(
						name = "Yoram Nagawker",
						email = "yoramnag@gmail.com"
				),
				license = @License(
						name = "Apache 2.0"
				)
		)
)
public class TransactionsApplication {

	public static void main(String[] args) {
		SpringApplication.run(TransactionsApplication.class, args);
	}

}
