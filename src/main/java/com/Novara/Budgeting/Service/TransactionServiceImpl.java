package com.Novara.Budgeting.Service;

import java.util.Optional;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.Novara.Budgeting.DTOs.TransactionDTO;
import com.Novara.Budgeting.Model.Source;
import com.Novara.Budgeting.Model.TransactionModel;
import com.Novara.Budgeting.Repository.TransactionRepository;

import lombok.RequiredArgsConstructor;

@Service
/*could use @RequiredContructor:
    - then you can remove explicitly making the constructor
    -
*/
public class TransactionServiceImpl implements TransactionService {
    private final Logger logger = LoggerFactory.getLogger(TransactionServiceImpl.class);
    private final  TransactionRepository transactionRepository;
   
    private final ModelMapper modelMapper;

    public TransactionServiceImpl(TransactionRepository transactionRepository, ModelMapper modelMapper){
        this.transactionRepository = transactionRepository;
        this.modelMapper = modelMapper;
    }


    public TransactionDTO createTransaction(TransactionDTO transactionDTO) {
        if(transactionDTO == null){
            throw new IllegalArgumentException("Transaction cannot be null");
        }

        String principal = SecurityContextHolder.getContext()
                        .getAuthentication()
                        .getPrincipal().
                         toString();

        UUID authId = UUID.fromString(principal);
        TransactionModel transaction = modelMapper.map(transactionDTO, TransactionModel.class);
        transaction.setAuthId(authId);
        TransactionModel savedTransaction = transactionRepository.save(transaction);
        return modelMapper.map(savedTransaction, TransactionDTO.class);
    }



    
    



    
    
}
