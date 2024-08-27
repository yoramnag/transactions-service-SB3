package com.example.transactions.dto;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "transactions")
public record TransactionsInfoDto(int maxTrsnsactionsPerADAy, int maxAmountPerADay) {
}
