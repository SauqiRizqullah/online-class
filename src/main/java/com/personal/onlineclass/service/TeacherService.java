package com.personal.onlineclass.service;

import com.personal.onlineclass.dto.request.SearchTeacherRequest;
import com.personal.onlineclass.dto.request.TeacherRequest;
import com.personal.onlineclass.dto.response.TeacherResponse;
import com.personal.onlineclass.entity.Teacher;
import org.springframework.data.domain.Page;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface TeacherService extends UserDetailsService {
    TeacherResponse createNewTeacher (TeacherRequest teacherRequest);
    Teacher getById (String teacherId);
    Page<Teacher> getAllTeachers (SearchTeacherRequest teacherRequest);
    String updateById (String teacherId, TeacherRequest teacherRequest);
    String deleteById (String teacherId);
    Teacher getByContext();
}
