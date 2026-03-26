package com.Novara.Budgeting.Controller;

import java.util.Arrays;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Novara.Budgeting.Auth.JwtService;
import com.Novara.Budgeting.DTOs.TransactionDTO;
import com.Novara.Budgeting.Service.TransactionService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/api/transaction")
@RequiredArgsConstructor
public class TransactionController {
    
    private final TransactionService transactionService;
    private final JwtService jwtService;

    @PostMapping("/create")
    public ResponseEntity<TransactionDTO>createTransaction(@Valid @RequestBody TransactionDTO transactionDTO){
        try{
        
           TransactionDTO savedTransaction = transactionService.createTransaction(transactionDTO);
           
            return new ResponseEntity<>(savedTransaction, HttpStatus.OK);

        }catch(Exception e){
    Throwable cause = e;
    while (cause.getCause() != null) {
        cause = cause.getCause();
    }
    System.out.println("ROOT CAUSE: " + cause.getMessage());
    throw e;
        }
    }




}
