package com.Novara.Budgeting.Service;

import java.util.Optional;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.Novara.Budgeting.DTOs.TransactionDTO;
import com.Novara.Budgeting.Model.TransactionModel;
import com.Novara.Budgeting.Repository.TransactionRepository;

import lombok.RequiredArgsConstructor;

@Service
/*could use @RequiredContructor:
    - then you can remove explicitly making the constructor
    -
*/
public class TransactionServiceImpl implements TransactionService {
    private final  TransactionRepository transactionRepository;
   
    private final ModelMapper modelMapper;

    public TransactionServiceImpl(TransactionRepository transactionRepository, ModelMapper modelMapper){
        this.transactionRepository = transactionRepository;
        this.modelMapper = modelMapper;
    }


    @Transactional
    public TransactionDTO createTransaction(TransactionDTO transactionDTO, UUID profileId) {
        if(transactionDTO == null){
            throw new IllegalArgumentException("Transaction cannot be null");
        }
        TransactionModel transaction = modelMapper.map(transactionDTO, TransactionModel.class);
        transaction.setAuthId(profileId);
        TransactionModel savedTransaction = transactionRepository.save(transaction);
        return modelMapper.map(savedTransaction, TransactionDTO.class);
    }



    



    
    
}
