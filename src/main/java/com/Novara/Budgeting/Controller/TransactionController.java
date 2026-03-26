package com.Novara.Budgeting.Controller;

import java.util.Arrays;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Novara.Budgeting.Auth.JwtService;
import com.Novara.Budgeting.Config.AppConstants;
import com.Novara.Budgeting.DTOs.TransactionDTO;
import com.Novara.Budgeting.DTOs.TransactionResponse;
import com.Novara.Budgeting.Service.TransactionService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/api/transactions")
@RequiredArgsConstructor
public class TransactionController {
    
    private final TransactionService transactionService;
    private final JwtService jwtService;


/* Manually create Transaction */
    @PostMapping("/manual")
    public ResponseEntity<TransactionDTO>createTransaction(@Valid @RequestBody TransactionDTO transactionDTO){
        try{
        
           TransactionDTO savedTransaction = transactionService.createTransaction(transactionDTO);
           
            return new ResponseEntity<>(savedTransaction, HttpStatus.CREATED);

        }catch(Exception e){
            System.out.println("Failed to create transation: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}

/* Update Transaction */

    @PutMapping("/{id}")
    public ResponseEntity<TransactionDTO> updateTransaction(@Valid @RequestBody TransactionDTO transactionDTO, @PathVariable("id") UUID transactionId){
            try{
                TransactionDTO updatedTransaction = transactionService.updateTransaction(transactionDTO, transactionId);
                return new ResponseEntity<>(updatedTransaction, HttpStatus.OK);
            }catch(Exception e){
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
    }



/*Deleting the Transaction */
@DeleteMapping("/{id}")
public ResponseEntity<?> deleteTransaction(@PathVariable("id") UUID transactionId){
    try{
        transactionService.deleteTransaction(transactionId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }catch(Exception e){
        System.out.println("Failed to delete transaction: " + e.getMessage());
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}


/*Getting All Transactions */
@GetMapping()
public ResponseEntity<TransactionResponse> getAllTransactions(@RequestParam(defaultValue = AppConstants.PAGE_NUMBER) Integer pageNumber,
@RequestParam(defaultValue = AppConstants.PAGE_SIZE) Integer pageSize,
@RequestParam(defaultValue = AppConstants.SORT_DIR) String sortOrder,
@RequestParam(defaultValue = AppConstants.SORT_CATEGORY_BY) String sortBy  ){
    TransactionResponse response = transactionService.getAllTransactions(pageNumber, pageSize, sortBy, sortOrder);
    return new ResponseEntity<>(response, HttpStatus.OK);
}

}
