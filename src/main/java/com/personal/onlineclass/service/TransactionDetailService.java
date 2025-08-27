package com.personal.onlineclass.service;

import com.personal.onlineclass.entity.TransactionDetail;

import java.util.List;

public interface TransactionDetailService {
    List<TransactionDetail> createBulk(List<TransactionDetail> transactionDetail);
}
