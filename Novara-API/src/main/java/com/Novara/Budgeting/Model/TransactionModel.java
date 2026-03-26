package com.Novara.Budgeting.Model;

import java.lang.ProcessBuilder.Redirect.Type;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.OffsetDateTime;
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
import jakarta.persistence.PrePersist;
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
@Table(name="transactions")
//Validations will Be added later
public class TransactionModel {
    @Id
   
    @Column(name = "transaction_id", updatable = false, nullable = false)
    private UUID id;

 @  PrePersist
    public void generateId() {
    if (this.id == null) {
        this.id = UUID.randomUUID();
    }
}

    @Column(name = "auth_id")
    private UUID authId;

    /*Big decimal because precision is important for math operations */
    @NotNull(message = "Amount must be filled in")
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
    @NotNull(message = "Source cannot be blank")
    private Source source;

    /**this will be changed to cater to timestampz */
    @NotNull(message = "Date cannot be blank")
    @Column(name = "date")
    private OffsetDateTime date;

    /**Usint instant as it works well for diff time zones */
    /*Since supabase takes care of the time, insertable false so jpa does not try to overide */
    @Column(name = "created_at", updatable = false, insertable = false)
    //@CreationTimestamp //automatically sets time after creation
    private OffsetDateTime createdAt;

    @Column(name = "updated_at")
    @UpdateTimestamp //Automatically updates after every update
    private OffsetDateTime updatedAt;


   
    
    
}