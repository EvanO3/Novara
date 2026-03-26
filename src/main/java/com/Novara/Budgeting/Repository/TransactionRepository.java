package com.Novara.Budgeting.Repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Novara.Budgeting.Model.TransactionModel;

public interface TransactionRepository extends JpaRepository<TransactionModel, UUID> {
    Optional<TransactionModel> findByIdAndAuthId(UUID transactionId, UUID authId);

}
