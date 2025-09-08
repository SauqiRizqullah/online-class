package com.personal.onlineclass.service.impl;

import com.personal.onlineclass.dto.request.TransactionRequest;
import com.personal.onlineclass.dto.response.TransactionDetailResponse;
import com.personal.onlineclass.dto.response.TransactionResponse;
import com.personal.onlineclass.entity.Course;
import com.personal.onlineclass.entity.Student;
import com.personal.onlineclass.entity.Transaction;
import com.personal.onlineclass.entity.TransactionDetail;
import com.personal.onlineclass.repository.TransactionRepository;
import com.personal.onlineclass.service.CourseService;
import com.personal.onlineclass.service.StudentService;
import com.personal.onlineclass.service.TransactionDetailService;
import com.personal.onlineclass.service.TransactionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;

    private final StudentService studentService;

    private final CourseService courseService;

    private final TransactionDetailService transactionDetailService;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public TransactionResponse createNewTransaction(TransactionRequest transactionRequest) {
        log.info("Creating a New Transaction!!!");
        log.info("");
        log.info("Finding a student object...");
        Student student = studentService.getById(transactionRequest.getStudentId());

        log.info("Building a transaction object...");
        Transaction trx = Transaction.builder()
                .student(student)
                .trxDate(new Date())
                .build();

//        Transaction savedTransaction = transactionRepository.save(trx);

        log.info("Adding transaction details...");
        List<TransactionDetail> trxDetail = transactionRequest.getTransactionDetails().stream()
                .map(detailRequest -> {
                    Course course = courseService.getById(detailRequest.getCourseId());

                    log.info("Trying to buy " + course.getTitle() + "'s course........");

                    return TransactionDetail.builder()
                            .transaction(trx)
                            .course(course)
                            .price(course.getPrice())
                            .build();
                }).toList();

        log.info("Successfully adding transaction details!!!");
        log.info("");
        log.info("Setting transaction details...");

        trx.setTransactionDetails(trxDetail);


        Long totalAmounts = trxDetail.stream()
                .mapToLong(TransactionDetail::getPrice)
                .sum();
        log.info("Setting transaction total amounts...");
        trx.setAmounts(totalAmounts);

        log.info("Saving transaction into database...");
        Transaction savedTransaction = transactionRepository.save(trx);

        log.info("Saving transaction details into database...");
        transactionDetailService.createBulk(trxDetail);

        log.info("Creating transcation detail response...");
        List<TransactionDetailResponse> trxDetailResponse = trxDetail.stream().map(
                detail -> {
                    return TransactionDetailResponse.builder()
                            .trxDetailId(detail.getTrxDetailId())
                            .courseId(detail.getCourse().getCourseId())
                            .price(detail.getPrice())
                            .build();
                }
        ).toList();


        log.info("Returning transaction response!!!");
        return TransactionResponse.builder()
                .trxId(savedTransaction.getTrxId())
                .studentId(savedTransaction.getStudent().getStudendId())
                .amounts(savedTransaction.getAmounts())
                .trxDate(savedTransaction.getTrxDate())
                .transactionDetailResponse(trxDetailResponse)
                .build();
    }

    @Transactional(readOnly = true)
    @Override
    public List<TransactionResponse> getAllTransactions() {

        log.info("Getting All Transactions!!!");
        log.info("");
        List<Transaction> transactions = transactionRepository.findAll();

        log.info("Retrieving all detail transactions...");
        return transactions.stream().map(trx -> {
            List<TransactionDetailResponse> trxDetailResponse = trx.getTransactionDetails().stream().map( trxDetail -> {
                return TransactionDetailResponse.builder()
                        .trxDetailId(trxDetail.getTrxDetailId())
                        .courseId(trxDetail.getCourse().getCourseId())
                        .price(trxDetail.getPrice())
                        .build();
            }).toList();

            log.info("Returning Transaction Details!!!");
            return TransactionResponse.builder()
                    .trxId(trx.getTrxId())
                    .studentId(trx.getStudent().getStudendId())
                    .trxDate(trx.getTrxDate())
                    .amounts(trx.getAmounts())
                    .transactionDetailResponse(trxDetailResponse)
                    .build();
        }).toList();

    }
}
