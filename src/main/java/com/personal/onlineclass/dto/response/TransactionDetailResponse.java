package com.personal.onlineclass.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class TransactionDetailResponse {
    private String trxDetailId;
    private String courseId;
    private Long price;
}
