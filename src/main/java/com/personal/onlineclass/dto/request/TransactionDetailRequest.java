package com.personal.onlineclass.dto.request;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TransactionDetailRequest {
    private String courseId;
}
