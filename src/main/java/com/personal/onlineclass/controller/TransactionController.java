package com.personal.onlineclass.controller;

import com.personal.onlineclass.constant.APIUrl;
import com.personal.onlineclass.dto.request.TransactionRequest;
import com.personal.onlineclass.dto.response.TransactionResponse;
import com.personal.onlineclass.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = APIUrl.TRANSACTION)
public class TransactionController {

    private final TransactionService transactionService;

    @PostMapping(produces = "application/json")
    public TransactionResponse createNewTransaction (
            @RequestBody TransactionRequest transactionRequest
    ){
        return transactionService.createNewTransaction(transactionRequest);
    }

    @GetMapping(produces = "application/json")
    public List<TransactionResponse> getAllTransactions (){
        return transactionService.getAllTransactions();
    }
}
