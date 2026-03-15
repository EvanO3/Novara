package com.Novara.Budgeting.Model;

import java.lang.ProcessBuilder.Redirect.Type;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.Date;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name="Transaction")
//Validations will Be added later
public class TransactionModel {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "T_id")
    private UUID Id;

    @Column(name = "auth_Id")
    private UUID authId;

    /*Big decimal because precision is important for math operations */
    @NotBlank(message = "Amount must be filled in")
    @DecimalMin(value = "0.00", message = "Budget must be positive")
    private BigDecimal amount;

    @NotBlank(message = "Type must be filled in")
    @Column(name = "type")
    private String type;

    @NotBlank(message = "Merchant cannot be blank")
    @Column(name = "merchant")
    private String merchant;

    @NotNull(message = "Description cannot be empty")
    @Column(name = "description")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "source")
    @NotBlank(message = "Source cannot be blank")
    private Source source;

    /**this will be changed to cater to timestampz */
    @NotBlank(message = "Date cannot be blank")
    @Column(name = "date")
    private Date date;

    /**Usint instant as it works well for diff time zones */
    @Column(name = "createdAt", updatable = false)
    @CreationTimestamp //automatically sets time after creation
    private Instant createdAt;

    @Column(name = "updatedAt")
    @UpdateTimestamp //Automatically updates after every update
    private Instant updatedAt;


}