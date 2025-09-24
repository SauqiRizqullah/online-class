package com.personal.onlineclass.service.impl;

import com.personal.onlineclass.constant.Field;
import com.personal.onlineclass.dto.request.SearchTeacherRequest;
import com.personal.onlineclass.dto.request.TeacherRequest;
import com.personal.onlineclass.dto.response.TeacherResponse;
import com.personal.onlineclass.entity.Teacher;
import com.personal.onlineclass.repository.TeacherRepository;
import com.personal.onlineclass.service.TeacherService;
import com.personal.onlineclass.specification.TeacherSpecification;
import com.personal.onlineclass.utils.ValidationUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
@Slf4j
@RequiredArgsConstructor
public class TeacherServiceImpl implements TeacherService {

    private final TeacherRepository teacherRepository;

    private final ValidationUtil validationUtil;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public TeacherResponse createNewTeacher(TeacherRequest teacherRequest) {
        log.info("Creating a New Teacher!!!");
        log.info("");
        log.info("Validating a teacher request....");
        validationUtil.validate(teacherRequest);

        log.info("Building a teacher object....");
        Teacher newTeacher = Teacher.builder()
                .teacherName(teacherRequest.getTeacherName())
                .email(teacherRequest.getEmail())
                .contactNumber(teacherRequest.getContactNumber())
                .field(Field.valueOf(teacherRequest.getField().toUpperCase()))
                .build();
        log.info("Saving data in database...");
        teacherRepository.saveAndFlush(newTeacher);

        log.info("Returning a teacher response...");
        log.info("");
        log.info("Creating in a serviceimpl success!!!");
        log.info("");

        return TeacherResponse.builder()
                .teacherId(newTeacher.getTeacherId())
                .teacherName(newTeacher.getTeacherName())
                .email(newTeacher.getEmail())
                .contactNumber(newTeacher.getContactNumber())
                .field(newTeacher.getField().name())
                .build();
    }

    @Transactional(readOnly = true)
    @Override
    public Teacher getById(String teacherId) {
        return teacherRepository.findById(teacherId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Teacher's ID was not found!!!"));
    }

    @Transactional(readOnly = true)
    @Override
    public Page<Teacher> getAllTeachers(SearchTeacherRequest teacherRequest) {
        // 1. Ketika nilai halaman 0, maka buatlah menjadi 1
        log.info("Getting All Teacher Data!!!");
        log.info("");
        if(teacherRequest.getPage() <= 0){
            log.info("Setting page from the first...");
            teacherRequest.setPage(1);
        }

        // 2. Membuat validasi pengurutan halaman dengan kolom - kolom yang tersedia
        String validSortBy;
        if("teacherName".equalsIgnoreCase(teacherRequest.getSortBy())){
            log.info("Sorting by teacher name...");
            validSortBy = teacherRequest.getSortBy();
        } else {
            log.info("Sorting by teacher id...");
            validSortBy = "teacherId";
        }

        // 3. Membuat aturan sortBy dengan objek sort
        log.info("Creating sortBy rule...");
        Sort sort = Sort.by(Sort.Direction.fromString(teacherRequest.getDirection()), validSortBy);

        //4. Membuat objek halaman Pageable untuk membuat sebuah halaman
        log.info("Creating a page object...");
        Pageable pageable = PageRequest.of(teacherRequest.getPage() - 1, teacherRequest.getSize(), sort);

        //5. Menyelaraskan dengan rule query dari specification milik objek itu
        log.info("Creating a specification...");
        Specification<Teacher> specification = TeacherSpecification.getSpecification(teacherRequest);

        log.info("Successfully Teacher Repository!!!");
        return teacherRepository.findAll(specification, pageable);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public String updateById(String teacherId, TeacherRequest teacherRequest) {
        log.info("Updating Teacher Data!!!");
        log.info("");
        validationUtil.validate(teacherRequest);

        log.info("Searching the teacher by ID...");
        Teacher teacher = teacherRepository.findById(teacherId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Teacher's ID was not found!!!"));

        log.info("Setting teacher's name...");
        teacher.setTeacherName(teacherRequest.getTeacherName());
        log.info("Setting teacher's email...");
        teacher.setEmail(teacherRequest.getEmail());
        log.info("Setting teacher's contact number...");
        teacher.setContactNumber(teacherRequest.getContactNumber());
        log.info("Setting teacher's field...");
        teacher.setField(Field.valueOf(teacherRequest.getField()));

        log.info("Validating teacher's object...");
        validationUtil.validate(teacher);

        log.info("Saving teacher's data into database...");
        teacherRepository.saveAndFlush(teacher);

        return teacher + "'s data has been updated!!!";
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public String deleteById(String teacherId) {
        log.info("Deleting Teacher's Object!!!");
        log.info("");
        log.info("Searching teacher's object by ID...");
        Teacher teacher = teacherRepository.findById(teacherId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Teacher's ID was not found!!!"));
        log.info("Deleting teacher data from database...");
        teacherRepository.delete(teacher);
        return teacher.getTeacherId() + "'s data has been deleted!!!";
    }

    private TeacherResponse parseTeacherToTeacherResponse(Teacher teacher){
        String teacherId;

        if(teacher.getTeacherId() == null){
            teacherId = null;
        } else {
            teacherId = teacher.getTeacherId();
        }

        return TeacherResponse.builder()
                .teacherId(teacherId)
                .teacherName(teacher.getTeacherName())
                .email(teacher.getEmail())
                .contactNumber(teacher.getContactNumber())
                .field(teacher.getField().toString())
                .build();
    }

}
