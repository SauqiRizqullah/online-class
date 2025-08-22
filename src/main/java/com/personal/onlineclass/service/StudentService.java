package com.personal.onlineclass.service;

import com.personal.onlineclass.dto.request.SearchStudentRequest;
import com.personal.onlineclass.dto.request.StudentRequest;
import com.personal.onlineclass.dto.response.StudentResponse;
import com.personal.onlineclass.entity.Student;
import org.springframework.data.domain.Page;

public interface StudentService {
    StudentResponse createNewStudent (StudentRequest studentRequest);
    Student getById (String studentId);
    Page<Student> getAllStudents (SearchStudentRequest studentRequest);
    String updateById (String studentId, StudentRequest studentRequest);
    String deleteById (String studentId);
}
