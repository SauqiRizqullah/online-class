package com.personal.onlineclass.service.impl;


import com.personal.onlineclass.dto.request.SearchStudentRequest;
import com.personal.onlineclass.dto.request.StudentRequest;
import com.personal.onlineclass.dto.response.StudentResponse;
import com.personal.onlineclass.entity.Student;
import com.personal.onlineclass.repository.StudentRepository;
import com.personal.onlineclass.service.StudentService;
import com.personal.onlineclass.specification.StudentSpecification;
import com.personal.onlineclass.utils.ValidationUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
@Slf4j
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;

    private final ValidationUtil validationUtil;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public StudentResponse createNewStudent(StudentRequest studentRequest) {
        log.info("Creating a New Student!!!");
        log.info("");
        log.info("Validating a student request....");
        validationUtil.validate(studentRequest);

        log.info("Building a student object...");
        Student newStudent = Student.builder()
                .studentName(studentRequest.getStudentName())
                .email(studentRequest.getEmail())
                .background(studentRequest.getBackground())
                .img(studentRequest.getImg())
                .build();
        log.info("Saving data in database...");
        studentRepository.saveAndFlush(newStudent);

        log.info("Returning a student response...");
        log.info("");
        log.info("Creating in a serviceimpl success!!!");
        log.info("");

        return StudentResponse.builder()
                .studendId(newStudent.getStudendId())
                .studentName(studentRequest.getStudentName())
                .email(studentRequest.getEmail())
                .background(studentRequest.getBackground())
                .img(studentRequest.getImg())
                .build();
    }

    @Transactional(readOnly = true)
    @Override
    public Student getById(String studentId) {

        return studentRepository.findById(studentId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Student's ID was not found!!!"));
    }

    @Transactional(readOnly = true)
    @Override
    public Page<Student> getAllStudents(SearchStudentRequest studentRequest) {
        log.info("Getting All Students Data!!!");
        log.info("");
        if (studentRequest.getPage() <= 0){
            log.info("Setting page from the first...");
            studentRequest.setPage(1);
        }

        String validSortBy;
        if("studentName".equalsIgnoreCase(studentRequest.getSortBy())) {
            log.info("Sorting by student name...");
            validSortBy = studentRequest.getSortBy();
        } else {
            log.info("Sorting by student id...");
            validSortBy = "studentId";
        }

        log.info("Creating sortBy rule...");
        Sort sort = Sort.by(Sort.Direction.fromString(studentRequest.getDirection()), validSortBy);

        log.info("Creating a page object...");
        Pageable pageable = PageRequest.of(studentRequest.getPage() -1, studentRequest.getSize(),sort);

        log.info("Creating a specification...");
        Specification<Student> specification = StudentSpecification.getSpecification(studentRequest);

        log.info("Successfully Student Repository!!!");
        return studentRepository.findAll(specification, pageable);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public String updateById(String studentId, StudentRequest studentRequest) {
        log.info("Updating Student Data!!!");
        log.info("");
        validationUtil.validate(studentRequest);

        log.info("Searching the student by ID...");
        Student student = studentRepository.findById(studentId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Student's ID was not found!!!"));

        log.info("Setting student's name...");
        student.setStudentName(studentRequest.getStudentName());
        log.info("Setting student's email...");
        student.setEmail(studentRequest.getEmail());
        log.info("Setting student's background...");
        student.setBackground(studentRequest.getBackground());
        log.info("Setting student's image...");
        student.setImg(studentRequest.getImg());

        log.info("Validating student's object...");
        validationUtil.validate(student);

        log.info("Saving student's data into database...");
        studentRepository.saveAndFlush(student);
        return studentId + "'s data has been updated!!!";
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public String deleteById(String studentId) {
        log.info("Deleting Student's Object!!!");
        log.info("");
        log.info("Searching student's object by ID...");
        Student student = studentRepository.findById(studentId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Student's ID was not found!!!"));
        log.info("Deleting student data from database...");
        studentRepository.delete(student);
        return student.getStudendId() + "'s data has been deleted!!!";
    }
}
