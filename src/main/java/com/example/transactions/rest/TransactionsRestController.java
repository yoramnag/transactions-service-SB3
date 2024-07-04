package com.example.transactions.rest;

import com.example.transactions.constants.TransactionsConstants;
import com.example.transactions.dto.ErrorResponseDto;
import com.example.transactions.dto.ResponseDto;
import com.example.transactions.entity.Transactions;
import com.example.transactions.service.TransactionsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Tag(
        name = "CRUD REST APIs for transactions ",
        description = "CRUD REST APIs in transactions to CREATE, FETCH AND DELETE transactions"
)
@RestController
@RequestMapping(path = "/api" , produces = MediaType.APPLICATION_JSON_VALUE)
@AllArgsConstructor
@Validated
public class TransactionsRestController {

    TransactionsService transactionsService;

    /**
     * get all records from transactions table
     * @return all transactions cards from the transactions table
     */
    @Operation(
            summary = "get all transactions REST API",
            description = "get all transactions from the transactions table"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP Status OK"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status Internal Server Error",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            )
    }
    )
    @GetMapping("/getAllTransactions")
    public ResponseEntity<List<Transactions>> getAllTransactions(){
        List<Transactions> transactionsList = transactionsService.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(transactionsList);
    }

    /**
     * get transaction by transactionId
     * @param transactionId
     * @return status OK if transaction exist in the transactions table
     */
    @Operation(
            summary = "get transaction REST API",
            description = "get transaction by transactionId"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP Status OK"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "HTTP Status Bad Request, transactionId must be only digits",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "HTTP Status transaction NOT found",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status Internal Server Error",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            )
    }
    )
    @GetMapping("/getTransaction")
    public ResponseEntity<Optional<Transactions>> getTransactionByTransactionId(@RequestParam
                                                                                    @Pattern(regexp = "(^$|[0-9])", message = "transactionId must be only digits")
                                                                                    int transactionId){
        Optional<Transactions> transaction = transactionsService.findTransactionByTransactionId(transactionId);
        return ResponseEntity.status(HttpStatus.OK).body(transaction);
    }

    /**
     * add new transaction to the transactions table
     * @param transaction transaction info
     * @return status created if the transaction was successfully added to the transactions table
     */
    @Operation(
            summary = "Create transaction REST API",
            description = "aad new transaction to the transactions table"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "HTTP Status CREATED"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "HTTP Status Fraud exception OR Luhn Exception",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status Internal Server Error",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            )
    }
    )
    @PostMapping("/saveTransaction")
    public ResponseEntity<ResponseDto> createTransaction(@Valid @RequestBody Transactions transaction){
        transactionsService.saveTransaction(transaction);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ResponseDto(TransactionsConstants.STATUS_201,TransactionsConstants.MESSAGE_201));
    }

    /**
     * delete transaction from the transactions table
     * @param transactionId
     * @return status OK if transaction was successfully deleted the transactions table
     */
    @Operation(
            summary = "delete transaction REST API",
            description = "delete transaction by transactionId"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP Status OK"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "HTTP Status Bad Request, transactionId must be only digits",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "HTTP Status transaction NOT found",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status Internal Server Error",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            )
    }
    )
    @DeleteMapping("/deleteTransaction")
    public ResponseEntity<ResponseDto> deleteTransactionByTransactionId(@RequestParam
                                                                            @Pattern(regexp = "(^$|[0-9])", message = "transactionId must be only digits")
                                                                            int transactionId){
        transactionsService.deleteTransactionByTransactionId(transactionId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ResponseDto(TransactionsConstants.STATUS_200,TransactionsConstants.MESSAGE_200));
    }
}
