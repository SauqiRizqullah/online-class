package com.personal.onlineclass.service.impl;

import com.personal.onlineclass.dto.request.CourseRequest;
import com.personal.onlineclass.dto.request.SearchCourseRequest;
import com.personal.onlineclass.dto.response.CourseResponse;
import com.personal.onlineclass.entity.Course;
import com.personal.onlineclass.entity.Teacher;
import com.personal.onlineclass.repository.CourseRepository;
import com.personal.onlineclass.service.CourseService;
import com.personal.onlineclass.service.TeacherService;
import com.personal.onlineclass.specification.CourseSpecification;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class CourseServiceImpl implements CourseService {

    private final CourseRepository courseRepository;
    private final TeacherService teacherService;

    @Transactional(rollbackFor = Exception.class)
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
                .teacher(teacher)
                .title(course.getTitle())
                .description(course.getTitle())
                .price(course.getPrice())
                .build();
    }

    @Transactional(readOnly = true)
    @Override
    public Course getById(String courseId) {
        return courseRepository.findById(courseId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Course ID was not found!!!"));
    }

    @Transactional(readOnly = true)
    @Override
    public Page<Course> getAllCourses(SearchCourseRequest courseRequest) {
        if (courseRequest.getPage() <= 0){
            courseRequest.setPage(1);
        }

        String validSortBy;
        if("title".equalsIgnoreCase(courseRequest.getSortBy())){
            validSortBy = courseRequest.getSortBy();
        } else {
            validSortBy = "courseId";
        }

        Sort sort = Sort.by(Sort.Direction.fromString(courseRequest.getDirection()), validSortBy);

        Pageable pageable = PageRequest.of(courseRequest.getPage() - 1, courseRequest.getSize(), sort);

        Specification<Course> specification = CourseSpecification.getSpecification(courseRequest);

        return courseRepository.findAll(specification, pageable);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public String updateById(String courseId, CourseRequest courseRequest) {
        Course course = courseRepository.findById(courseId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Course ID was not found!!!"));

        course.setTitle(courseRequest.getTitle());
        course.setDescription(courseRequest.getDescription());
        course.setPrice(course.getPrice());

        courseRepository.saveAndFlush(course);
        return "Course' detaiils have been updated!!!";
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public String deleteById(String courseId) {
        Course course = courseRepository.findById(courseId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Course ID was not found!!!"));
        courseRepository.delete(course);
        return "Course data has been deleted!!!";
    }
}
