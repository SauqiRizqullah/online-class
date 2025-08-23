package com.personal.onlineclass.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.personal.onlineclass.constant.ConstantTable;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name = ConstantTable.TRANSACTION_DETAIL)
public class TransactionDetail {

    @Id
    @GeneratedValue(generator = "custom-id")
    @GenericGenerator(name = "custom-id", strategy = "com.personal.onlineclass.utils.TransactionDetailCustomId")
    @Column(name = "trx_detail_id")
    private String trxDetailId;

    @ManyToOne
    @JoinColumn(name = "trx_id", nullable = false)
    @JsonBackReference
    private Transaction transaction;

    @ManyToOne
    @JoinColumn(name = "course_id", nullable = false)
    private Course course;

    @Column(name = "price", nullable = false)
    private Long price;
}
