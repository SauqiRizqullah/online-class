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
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;

    private final ValidationUtil validationUtil;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public StudentResponse createNewStudent(StudentRequest studentRequest) {
        validationUtil.validate(studentRequest);

        Student newStudent = Student.builder()
                .studentName(studentRequest.getStudentName())
                .email(studentRequest.getEmail())
                .background(studentRequest.getBackground())
                .img(studentRequest.getImg())
                .build();
        studentRepository.saveAndFlush(newStudent);

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

        if (studentRequest.getPage() <= 0){
            studentRequest.setPage(1);
        }

        String validSortBy;
        if("studentName".equalsIgnoreCase(studentRequest.getSortBy())) {
            validSortBy = studentRequest.getSortBy();
        } else {
            validSortBy = "studentId";
        }

        Sort sort = Sort.by(Sort.Direction.fromString(studentRequest.getDirection()), validSortBy);

        Pageable pageable = PageRequest.of(studentRequest.getPage() -1, studentRequest.getSize(),sort);

        Specification<Student> specification = StudentSpecification.getSpecification(studentRequest);

        return studentRepository.findAll(specification, pageable);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public String updateById(String studentId, StudentRequest studentRequest) {

        validationUtil.validate(studentRequest);

        Student student = studentRepository.findById(studentId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Student's ID was not found!!!"));

        student.setStudentName(studentRequest.getStudentName());
        student.setEmail(studentRequest.getEmail());
        student.setBackground(studentRequest.getBackground());
        student.setImg(studentRequest.getImg());

        validationUtil.validate(student);

        studentRepository.saveAndFlush(student);
        return studentId + "'s data has been updated!!!";
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public String deleteById(String studentId) {
        Student student = studentRepository.findById(studentId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Student's ID was not found!!!"));
        studentRepository.delete(student);
        return student.getStudendId() + "'s data has been deleted!!!";
    }
}
