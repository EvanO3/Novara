package com.Novara.Budgeting.Service;
import java.util.UUID;

import com.Novara.Budgeting.DTOs.TransactionDTO;
import com.Novara.Budgeting.DTOs.TransactionResponse;

public interface TransactionService {

     /*CRUD TRANSACTION  */

    TransactionResponse getAllTransactions(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder);
    TransactionDTO createTransaction(TransactionDTO transactionDTO);
    TransactionDTO updateTransaction(TransactionDTO transactionDTO,  UUID transactionId);
    void deleteTransaction(UUID transactionID);

}
