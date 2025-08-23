package com.personal.onlineclass.service.impl;

import com.personal.onlineclass.dto.request.CourseRequest;
import com.personal.onlineclass.dto.response.CourseResponse;
import com.personal.onlineclass.entity.Course;
import com.personal.onlineclass.entity.Teacher;
import com.personal.onlineclass.repository.CourseRepository;
import com.personal.onlineclass.service.CourseService;
import com.personal.onlineclass.service.TeacherService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class CourseServiceImpl implements CourseService {

    private final CourseRepository courseRepository;
    private final TeacherService teacherService;


    @Override
    public CourseResponse createNewCourse(CourseRequest courseRequest) {
        Teacher teacher = teacherService.getById(courseRequest.getTeacherId());

        Course course = Course.builder()
                .teacher(teacher)
                .title(courseRequest.getTitle())
                .description(courseRequest.getDescription())
                .price(courseRequest.getPrice())
                .build();

        courseRepository.saveAndFlush(course);

        return CourseResponse.builder()
                .courseId(course.getCourseId())
                .teacherResponse(teacher)
                .title(course.getTitle())
                .description(course.getTitle())
                .price(course.getPrice())
                .build();
    }

    @Override
    public Course getById(String courseId) {
        return courseRepository.findById(courseId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Course ID was not found!!!"));
    }

    @Override
    public Page<Course> getAllCourses(SearchCourseRequest courseRequest) {
        return null;
    }

    @Override
    public String updateById(String courseId, CourseRequest courseRequest) {
        Course course = courseRepository.findById(courseId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Course ID was not found!!!"));


        return "";
    }

    @Override
    public String deleteById(String courseId) {
        Course course = courseRepository.findById(courseId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Course ID was not found!!!"));
        courseRepository.delete(course);
        return "Course data has been deleted!!!";
    }
}
