package com.personal.onlineclass.controller;

import com.personal.onlineclass.constant.APIUrl;
import com.personal.onlineclass.dto.request.SearchTeacherRequest;
import com.personal.onlineclass.dto.request.TeacherRequest;
import com.personal.onlineclass.dto.response.PagingResponse;
import com.personal.onlineclass.dto.response.TeacherResponse;
import com.personal.onlineclass.entity.Teacher;
import com.personal.onlineclass.service.TeacherService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = APIUrl.TEACHER)
public class TeacherController {

    private final TeacherService teacherService;

    @PostMapping(produces = "application/json")
    public ResponseEntity<TeacherResponse> createNewTeacher(
            @RequestBody TeacherRequest teacherRequest
            )
    {
        // 1. Membuat objek menu dari service
        TeacherResponse newTeacher = teacherService.createNewTeacher(teacherRequest);

        // 2. Mengembalikan Response
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(newTeacher);
    }

    @GetMapping(path = APIUrl.PATH_TEACHER_ID, produces = "application/json")
    public ResponseEntity<Teacher> getTeacherById(
            @PathVariable String teacherId
            )
    {
        // 1. Membuat objek Teacher Response
        Teacher teacher = teacherService.getById(teacherId);

        return ResponseEntity.ok(teacher);
    }

    @GetMapping(produces = "application/json")
    public ResponseEntity<PagingResponse> getAllTeachers (
            @RequestParam(name = "page", defaultValue = "1") Integer page,
            @RequestParam(name = "size", defaultValue = "10") Integer size,
            @RequestParam(name = "sortBy", defaultValue = "teacherId") String sortBy,
            @RequestParam(name = "direction", defaultValue = "ASC") String direction,
            @RequestParam(name = "teacherName", required = false) String teacherName
    ){
        // 1. Membuat objek SearchMenuRequest untuk mencari Teacher semuanya
        SearchTeacherRequest searchTeacherRequest = SearchTeacherRequest.builder()
                .page(page)
                .size(size)
                .sortBy(sortBy)
                .direction(direction)
                .teacherName(teacherName)
                .build();

        // 2. Membuat objek Page Teacher
        Page<Teacher> allTeachers = teacherService.getAllTeachers(searchTeacherRequest);

        // 3. Membuat objek paging
        PagingResponse pagingResponse = PagingResponse.builder()
                .page(allTeachers.getPageable().getPageNumber() + 1)
                .size(allTeachers.getPageable().getPageSize())
                .totalPages(allTeachers.getTotalPages())
                .totalElements(allTeachers.getTotalElements())
                .hasNext(allTeachers.hasNext())
                .hasPrevious(allTeachers.hasPrevious())
                .build();

        // 4. Mengembalikan Paging Response
        return ResponseEntity.ok(pagingResponse);
    }

    @PutMapping(path = APIUrl.PATH_TEACHER_ID, produces = "application/json")
    public ResponseEntity<String> updateById (
        @PathVariable String teacherId,
        @RequestBody TeacherRequest teacherRequest
    ){
        // 1. Memanggil service untuk me
        String dataUpdate = teacherService.updateById(teacherId, teacherRequest);

        return ResponseEntity.ok(dataUpdate);
    }

    @DeleteMapping(path = APIUrl.PATH_TEACHER_ID, produces = "application/json")
    public ResponseEntity<String> deleteById (
            @PathVariable String teacherId
    ) {
        String dataDelete = teacherService.deleteById(teacherId);

        return ResponseEntity.ok(dataDelete);
    }
}
