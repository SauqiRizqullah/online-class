package com.personal.onlineclass.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.personal.onlineclass.constant.ConstantTable;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import java.util.Date;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name = ConstantTable.TRANSACTION)
public class Transaction {
    @Id
    @GeneratedValue(generator = "custom-id")
    @GenericGenerator(name = "custom-id", strategy = "com.personal.onlineclass.utils.TransactionCustomId")
    @Column(name = "trx_id")
    private String trxId;

    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student student;

    @Column(name = "amounts")
    private Long amounts;

    @Temporal(TemporalType.DATE)
    @Column(name = "trx_date", updatable = false)
    private Date trxDate;

    @OneToMany(mappedBy = "transaction")
    @JsonManagedReference
    private List<TransactionDetail> transactionDetails;
}
