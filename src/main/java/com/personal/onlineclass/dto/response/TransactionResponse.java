package com.personal.onlineclass.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Setter
@Getter
@Builder
public class TransactionResponse {
    private String trxId;
    private String studentId;
    private Long amounts;
    private Date trxDate;
    private List<TransactionDetailResponse> transactionDetailResponse;
}
