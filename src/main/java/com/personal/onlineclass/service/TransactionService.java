package com.personal.onlineclass.service;

import com.personal.onlineclass.dto.request.TransactionRequest;
import com.personal.onlineclass.dto.response.TransactionResponse;

import java.util.List;

public interface TransactionService {

    TransactionResponse createNewTransaction (TransactionRequest transactionRequest);

    List<TransactionResponse> getAllTransactions();
}
