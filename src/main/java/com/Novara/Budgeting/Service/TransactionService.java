package com.Novara.Budgeting.Service;

import java.util.UUID;

import com.Novara.Budgeting.DTOs.TransactionDTO;

public interface TransactionService {

    TransactionDTO createTransaction(TransactionDTO transactionDTO, UUID profileId);
}
