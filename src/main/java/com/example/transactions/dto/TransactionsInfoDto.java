package com.example.transactions.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "transactions")
@Getter
@Setter
public class TransactionsInfoDto{

    private int maxTrsnsactionsPerADAy;
    private int maxAmountPerADay;
}
