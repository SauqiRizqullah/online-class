package com.personal.onlineclass.controller;

import com.personal.onlineclass.constant.APIUrl;
import com.personal.onlineclass.dto.request.CourseRequest;
import com.personal.onlineclass.dto.request.SearchCourseRequest;
import com.personal.onlineclass.dto.response.CourseResponse;
import com.personal.onlineclass.dto.response.PagingResponse;
import com.personal.onlineclass.entity.Course;
import com.personal.onlineclass.service.CourseService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = APIUrl.COURSE)
public class CourseController {
    private final CourseService courseService;

    @PostMapping(produces = "application/json")
    public ResponseEntity<CourseResponse> createNewCourse(
            @RequestBody CourseRequest courseRequest
            )
    {
        CourseResponse newCourse = courseService.createNewCourse(courseRequest);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(newCourse);
    }

    @GetMapping(path = APIUrl.PATH_COURSE_ID, produces = "application/json")
    public ResponseEntity<Course> getCourseById(
            @PathVariable String courseId
    ){
        Course course = courseService.getById(courseId);

        return ResponseEntity.ok(course);
    }

    @GetMapping(produces = "application/json")
    public ResponseEntity<PagingResponse> getAllCourses (
            @RequestParam(name = "page", defaultValue = "1") Integer page,
            @RequestParam(name = "size", defaultValue = "10") Integer size,
            @RequestParam(name = "sortBy", defaultValue = "courseId") String sortBy,
            @RequestParam(name = "direction", defaultValue = "ASC") String direction,
            @RequestParam(name = "title", required = false) String title
    ) {
        SearchCourseRequest searchCourseRequest = SearchCourseRequest.builder()
                .page(page)
                .size(size)
                .sortBy(sortBy)
                .direction(direction)
                .title(title)
                .build();

        Page<Course> allCourses = courseService.getAllCourses(searchCourseRequest);

        PagingResponse pagingResponse = PagingResponse.builder()
                .page(allCourses.getPageable().getPageNumber() + 1)
                .size(allCourses.getPageable().getPageSize())
                .totalPages(allCourses.getTotalPages())
                .totalElements(allCourses.getTotalElements())
                .hasNext(allCourses.hasNext())
                .hasPrevious(allCourses.hasPrevious())
                .build();

        return ResponseEntity.ok(pagingResponse);
    }

    @PutMapping(path = APIUrl.PATH_COURSE_ID, produces = "application/json")
    public ResponseEntity<String> updateCourseById (
            @PathVariable String courseId,
            @RequestBody CourseRequest courseRequest
    ) {
        String dataUpdate = courseService.updateById(courseId, courseRequest);

        return ResponseEntity.ok(dataUpdate);
    }

    @DeleteMapping(path = APIUrl.PATH_COURSE_ID, produces = "application/json")
    public ResponseEntity<String> deleteCourseById(
            @PathVariable String courseId
    ){
        String dataDelete = courseService.deleteById(courseId);

        return ResponseEntity.ok(dataDelete);
    }
}
