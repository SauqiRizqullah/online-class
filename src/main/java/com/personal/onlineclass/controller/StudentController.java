package com.personal.onlineclass.controller;

import com.personal.onlineclass.constant.APIUrl;
import com.personal.onlineclass.dto.request.SearchStudentRequest;
import com.personal.onlineclass.dto.request.StudentRequest;
import com.personal.onlineclass.dto.response.PagingResponse;
import com.personal.onlineclass.dto.response.StudentResponse;
import com.personal.onlineclass.entity.Student;
import com.personal.onlineclass.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = APIUrl.STUDENT)
public class StudentController {

    private final StudentService studentService;

    @PostMapping(produces = "application/json")
    public ResponseEntity<StudentResponse> createNewStudent(
            @RequestBody StudentRequest studentRequest
            )
    {
        StudentResponse newStudent = studentService.createNewStudent(studentRequest);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(newStudent);
    }

    @GetMapping(path = APIUrl.PATH_STUDENT_ID, produces = "application/json")
    public ResponseEntity<Student> getStudentById(
            @PathVariable String studentId
    ){
        Student student = studentService.getById(studentId);

        return ResponseEntity.ok(student);
    }

    @GetMapping(produces = "application/json")
    public ResponseEntity<PagingResponse> getAllStudents (
            @RequestParam(name = "page", defaultValue = "1") Integer page,
            @RequestParam(name = "size", defaultValue = "10") Integer size,
            @RequestParam(name = "sortBy", defaultValue = "studentId") String sortBy,
            @RequestParam(name = "direction", defaultValue = "ASC") String direction,
            @RequestParam(name = "studentName", required = false) String studentName
    ){
        SearchStudentRequest searchStudentRequest = SearchStudentRequest.builder()
                .page(page)
                .size(size)
                .sortBy(sortBy)
                .direction(direction)
                .studentName(studentName)
                .build();
        Page<Student> allStudents = studentService.getAllStudents(searchStudentRequest);

        PagingResponse pagingResponse = PagingResponse.builder()
                .page(allStudents.getPageable().getPageNumber())
                .size(allStudents.getPageable().getPageSize())
                .totalPages(allStudents.getTotalPages())
                .totalElements(allStudents.getTotalElements())
                .hasNext(allStudents.hasNext())
                .hasPrevious(allStudents.hasPrevious())
                .build();

        return ResponseEntity.ok(pagingResponse);
    }

    @PutMapping(path = APIUrl.PATH_STUDENT_ID, produces = "application/json")
    public ResponseEntity<String> updateStudentById (
            @PathVariable String studentId,
            @RequestBody StudentRequest studentRequest
    ) {
        String dataUpdate = studentService.updateById(studentId, studentRequest);

        return ResponseEntity.ok(dataUpdate);
    }

    @DeleteMapping(path = APIUrl.PATH_STUDENT_ID, produces = "application/json")
    public ResponseEntity<String> deleteStudentById (
            @PathVariable String studentId
    ){
        String dataDelete = studentService.deleteById(studentId);

        return ResponseEntity.ok(dataDelete);
    }
}
