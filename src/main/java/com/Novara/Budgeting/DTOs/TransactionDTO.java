package com.Novara.Budgeting.DTOs;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

import jakarta.persistence.Column;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransactionDTO {
    
    @NotNull(message = "Amount must be filled in")
    @DecimalMin(value = "0.00", message = "Budget must be positive")
    private BigDecimal amount;

    @NotBlank(message = "Type must be filled in")
    @Column(name = "type")
    private String type;

    @NotBlank(message = "Merchant cannot be blank")
    @Column(name = "merchant")
    private String merchant;

    @NotBlank(message = "Description cannot be empty")
    @Column(name = "description")
    private String description;

    /**this will be changed to cater to timestampz */
    @NotNull(message = "Date cannot be blank")
    @Column(name = "date")
    private OffsetDateTime date;
}
