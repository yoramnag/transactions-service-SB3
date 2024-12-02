package com.example.transactions.service;

import com.example.transactions.exception.ServiceUnavailableException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import com.example.transactions.dto.BlackListDto;
import com.example.transactions.dto.ResponseDto;

@Component()
public class BlackListFallback implements BlackListProxy{


    @Override
    public boolean checkIfCreditCradAllReadyExist(String correlationId, String creditCardNumber) {
        throw new ServiceUnavailableException("black-list-service is unavailable !!! please contact DevOps team ");
    }

    @Override
    public ResponseEntity<ResponseDto> createBlackListCard(String correlationId, BlackListDto blackListDto) {
        throw new ServiceUnavailableException("black-list-service is unavailable !!! please contact DevOps team ");
    }
}
