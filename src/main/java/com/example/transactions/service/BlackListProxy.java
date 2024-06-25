package com.example.transactions.service;

import com.example.transactions.dto.BlackListDto;
import com.example.transactions.dto.ResponseDto;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "black-list-service")
public interface BlackListProxy {

    @GetMapping("api/checkBlacklist")
    public boolean checkIfCreditCradAllReadyExist(@RequestParam
                                                              @Pattern(regexp = "(^$|[0-9]{16})", message = "credit card must be 16 digits")
                                                              String creditCardNumber);

    @PostMapping("api/createBlacklist")
    public ResponseEntity<ResponseDto> createBlackListCard(@Valid @RequestBody BlackListDto blackListDto);
}
