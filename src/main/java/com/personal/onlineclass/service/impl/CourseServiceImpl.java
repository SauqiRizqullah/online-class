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
import com.personal.onlineclass.utils.ValidationUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
@RequiredArgsConstructor
public class CourseServiceImpl implements CourseService {

    private final CourseRepository courseRepository;
    private final TeacherService teacherService;

    private final ValidationUtil validationUtil;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public CourseResponse createNewCourse(CourseRequest courseRequest) {
        log.info("Creating a New Course!!!");
        log.info("");
        log.info("Finding a teacher object...");
        Teacher teacher = teacherService.getById(courseRequest.getTeacherId());

        log.info("Validating a teacher object....");
        validationUtil.validate(teacher);

        log.info("Validating a course request....");
        validationUtil.validate(courseRequest);

        log.info("Building a course object....");
        Course course = Course.builder()
                .teacher(teacher)
                .title(courseRequest.getTitle())
                .description(courseRequest.getDescription())
                .price(courseRequest.getPrice())
                .build();

        log.info("Saving data in database...");
        courseRepository.saveAndFlush(course);

        log.info("Returning a course response...");
        log.info("");
        log.info("Creating in a serviceimpl success!!!");
        log.info("");
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
        log.info("Getting All Courses Data!!!");
        log.info("");
        if (courseRequest.getPage() <= 0){
            log.info("Setting page from the first...");
            courseRequest.setPage(1);
        }

        String validSortBy;
        if("title".equalsIgnoreCase(courseRequest.getSortBy())){
            log.info("Sorting by course title...");
            validSortBy = courseRequest.getSortBy();
        } else {
            log.info("Sorting by course id...");
            validSortBy = "courseId";
        }

        log.info("Creating sortBy rule...");
        Sort sort = Sort.by(Sort.Direction.fromString(courseRequest.getDirection()), validSortBy);

        log.info("Creating a page object...");
        Pageable pageable = PageRequest.of(courseRequest.getPage() - 1, courseRequest.getSize(), sort);

        log.info("Creating a specification...");
        Specification<Course> specification = CourseSpecification.getSpecification(courseRequest);

        log.info("Successfully Course Repository!!!");
        return courseRepository.findAll(specification, pageable);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public String updateById(String courseId, CourseRequest courseRequest) {
        log.info("Updating Course Data!!!");
        log.info("");
        validationUtil.validate(courseRequest);

        log.info("Searching the course by ID...");
        Course course = courseRepository.findById(courseId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Course ID was not found!!!"));

        log.info("Setting course's title...");
        course.setTitle(courseRequest.getTitle());
        log.info("Setting course's description...");
        course.setDescription(courseRequest.getDescription());
        log.info("Setting course's price...");
        course.setPrice(course.getPrice());

        log.info("Validating course's object...");
        validationUtil.validate(course);

        log.info("Saving teacher's data into database...");
        courseRepository.saveAndFlush(course);
        return "Course's details have been updated!!!";
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public String deleteById(String courseId) {
        log.info("Deleting Course's Object!!!");
        log.info("");
        log.info("Searching course's object by ID...");
        Course course = courseRepository.findById(courseId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Course ID was not found!!!"));
        log.info("Deleting course data from database...");
        courseRepository.delete(course);
        return "Course data has been deleted!!!";
    }
}
