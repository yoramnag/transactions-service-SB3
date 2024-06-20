package com.example.transactions.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@AllArgsConstructor
@Data
@Schema(
        name = "ErrorResponse",
        description = "Schema to hold error response information"
)
public class ErrorResponseDto {

    @Schema(
            description = "API path invoked by client",
            example = "uri=/api/getBlacklist"
    )
    private String apiPath;

    @Schema(
            description = "Error code representing the error happened",
            example = "NOT_FOUND"
    )
    private HttpStatus errorCode;

    @Schema(
            description = "Error message representing the error happened",
            example = "black list card not found with the given input " +
                    "data Credit Card : 'be05a4a8c3be5812f3b1df5bafef5d6ff4eaf3323b31326ef9f12a4d1a682f0f'"
    )
    private  String errorMessage;

    @Schema(
            description = "Time representing when the error happened",
            example = "2024-06-18T14:37:14.0755028"
    )
    private LocalDateTime errorTime;

}
