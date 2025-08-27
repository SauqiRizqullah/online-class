package com.personal.onlineclass.dto.request;

import com.personal.onlineclass.entity.TransactionDetail;
import lombok.*;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TransactionRequest {
    private String studentId;
    private List<TransactionDetailRequest> transactionDetails;
}
