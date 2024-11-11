package com.example.transactions.service;

import com.example.transactions.dto.BlackListDto;
import com.example.transactions.dto.ResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "black-list-service", fallback = BlackListFallback.class)
public interface BlackListProxy {

    @GetMapping("api/checkBlacklist")
    public boolean checkIfCreditCradAllReadyExist(@RequestHeader("creditCard-correlation-id") String correlationId,@RequestParam String creditCardNumber);

    @PostMapping("api/createBlacklist")
    public ResponseEntity<ResponseDto> createBlackListCard(@RequestHeader("creditCard-correlation-id") String correlationId,@RequestBody BlackListDto blackListDto);
}
