package com.example.transactions.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
@Schema(
        name = "Black List",
        description = "Schema to hold black list cars INFO"
)
public class BlackListDto {

    @Schema(
            description = "credit card number",
            example = "be05a4a8c3be5812f3b1df5bafef5d6ff4eaf3323b31326ef9f12a4d1a682f0f"
    )
    @NotEmpty(message = "credit card can NOT be null or empty")
    @Pattern(regexp = "(^$|[0-9]{16})", message = "credit card must be 16 digits")
    private String creditCard;

    @Schema(
            description = "mask credit card number",
            example = "XXXXXXXXXXXX0265"
    )
    private String maskCreditCard;

}
