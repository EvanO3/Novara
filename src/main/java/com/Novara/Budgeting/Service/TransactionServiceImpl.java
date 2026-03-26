package com.Novara.Budgeting.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.Novara.Budgeting.DTOs.TransactionDTO;
import com.Novara.Budgeting.DTOs.TransactionResponse;
import com.Novara.Budgeting.Exceptions.ResourceNotFound;
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

    @Qualifier("updateModelMapper")
    private final ModelMapper updateModelMapper;

    public TransactionServiceImpl(TransactionRepository transactionRepository, ModelMapper modelMapper, ModelMapper updateModelMapper){
        this.transactionRepository = transactionRepository;
        this.modelMapper = modelMapper;
        this.updateModelMapper=updateModelMapper;
    }


    @Transactional
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


    @Override
    public TransactionResponse getAllTransactions(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder) {
      Sort sortAndOrderBy = sortOrder.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();

      PageRequest pageDetails = PageRequest.of(pageNumber, pageSize, sortAndOrderBy);

      Page<TransactionModel> pageOfTransactions = transactionRepository.findAll(pageDetails);
      List<TransactionModel> transactions = pageOfTransactions.getContent();
      if(transactions.isEmpty()){
        throw new ResourceNotFound("Failed to find transaction data");
      }


      List<TransactionDTO> transactionDTOs =transactions.stream().map(transaction -> 
        modelMapper.map(transaction, TransactionDTO.class)).toList();

        TransactionResponse transactionResponse = new TransactionResponse();
        transactionResponse.setContent(transactionDTOs);
        transactionResponse.setPageNumber(pageOfTransactions.getNumber());
        transactionResponse.setPageSize(pageOfTransactions.getSize());
        transactionResponse.setLastPage(pageOfTransactions.isLast());
        transactionResponse.setTotalElements(pageOfTransactions.getTotalElements());
        transactionResponse.setTotalPages(pageOfTransactions.getTotalPages());
        return transactionResponse;
    }


    @Override
    @Transactional
    public TransactionDTO updateTransaction(TransactionDTO transactionDTO, UUID transactionId) {
        if(transactionDTO == null || transactionId == null){
            throw new IllegalArgumentException("Transaction cannot be null");
        }
        String principal = SecurityContextHolder.getContext()
        .getAuthentication().
        getPrincipal()
        .toString();

        /*Getting the UUID from the user */
        UUID authId = UUID.fromString(principal);

        /*Try to find the transaction */
        TransactionModel foundTransaction = transactionRepository.findByIdAndAuthId(transactionId, authId).orElseThrow(() -> new ResourceNotFound("Failed to find Transaction with id: " + transactionId));

        /*Map the saved DTO to the found transaction then resave */
        updateModelMapper.map(transactionDTO, foundTransaction);
        TransactionModel savedTransaction =transactionRepository.save(foundTransaction);
        TransactionDTO usersUpdatedTransaction = modelMapper.map(savedTransaction, TransactionDTO.class);
        return usersUpdatedTransaction;
    }



    @Override
    @Transactional
    public void deleteTransaction(UUID transactionId) {
        if(transactionId == null){
            throw  new IllegalArgumentException("Transaction Id cannot be null");
        }
        String principal = SecurityContextHolder.getContext()
        .getAuthentication()
        .getPrincipal()
        .toString();
        UUID authId = UUID.fromString(principal);

        TransactionModel userTransaction = transactionRepository.findByIdAndAuthId(transactionId, authId).orElseThrow(() -> new ResourceNotFound("Failed to find Transaction with id: " + transactionId));
        transactionRepository.delete(userTransaction);


    }



    
    



    
    
}
