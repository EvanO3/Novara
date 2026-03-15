package com.Novara.Budgeting.Controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    // @PostMapping("/create")
    // public ResponseEntity<TransactionDTO>createTransaction(@Valid @RequestBody TransactionDTO transactionDTO, HttpServletRequest request){
    //     Profile userId = jwtUtils.provideUserIdFromRequest(request);
    //    /TransactionDTO savedTransaction = transactionService.createTransaction(transactionDTO, userId);
    //   return new ResponseEntity<>(savedTransaction, HttpStatus.OK);
    // }


    @GetMapping("/hello")
    public String getMethodName() {
        return "Hello World";
    }
    


}
