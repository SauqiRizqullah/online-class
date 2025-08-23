package com.personal.onlineclass.service;

import com.personal.onlineclass.dto.request.CourseRequest;
import com.personal.onlineclass.dto.request.SearchCourseRequest;
import com.personal.onlineclass.dto.response.CourseResponse;
import com.personal.onlineclass.entity.Course;
import org.springframework.data.domain.Page;

public interface CourseService {
    CourseResponse createNewCourse (CourseRequest courseRequest);
    Course getById (String courseId);
    Page<Course> getAllCourses (SearchCourseRequest courseRequest);
    String updateById(String courseId, CourseRequest courseRequest);
    String deleteById(String courseId);
}
