package com.personal.onlineclass.service.impl;

import com.personal.onlineclass.constant.Field;
import com.personal.onlineclass.dto.request.SearchTeacherRequest;
import com.personal.onlineclass.dto.request.TeacherRequest;
import com.personal.onlineclass.dto.response.TeacherResponse;
import com.personal.onlineclass.entity.Teacher;
import com.personal.onlineclass.repository.TeacherRepository;
import com.personal.onlineclass.service.TeacherService;
import com.personal.onlineclass.specification.TeacherSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class TeacherServiceImpl implements TeacherService {

    private final TeacherRepository teacherRepository;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public TeacherResponse createNewTeacher(TeacherRequest teacherRequest) {
        Teacher newTeacher = Teacher.builder()
                .teacherName(teacherRequest.getTeacherName())
                .email(teacherRequest.getEmail())
                .contactNumber(teacherRequest.getContactNumber())
                .field(Field.valueOf(teacherRequest.getField().toUpperCase()))
                .build();
        teacherRepository.saveAndFlush(newTeacher);

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
        if(teacherRequest.getPage() <= 0){
            teacherRequest.setPage(1);
        }

        // 2. Membuat validasi pengurutan halaman dengan kolom - kolom yang tersedia
        String validSortBy;
        if("teacherName".equalsIgnoreCase(teacherRequest.getSortBy())){
            validSortBy = teacherRequest.getSortBy();
        } else {
            validSortBy = "teacherId";
        }

        // 3. Membuat aturan sortBy dengan objek sort
        Sort sort = Sort.by(Sort.Direction.fromString(teacherRequest.getDirection()), validSortBy);

        //4. Membuat objek halaman Pageable untuk membuat sebuah halaman
        Pageable pageable = PageRequest.of(teacherRequest.getPage() - 1, teacherRequest.getSize(), sort);

        //5. Menyelaraskan dengan rule query dari specification milik objek itu
        Specification<Teacher> specification = TeacherSpecification.getSpecification(teacherRequest);

        return teacherRepository.findAll(specification, pageable);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public String updateById(String teacherId, TeacherRequest teacherRequest) {
        Teacher teacher = teacherRepository.findById(teacherId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Teacher's ID was not found!!!"));

        teacher.setTeacherName(teacherRequest.getTeacherName());
        teacher.setEmail(teacherRequest.getEmail());
        teacher.setContactNumber(teacherRequest.getContactNumber());
        teacher.setField(Field.valueOf(teacherRequest.getField()));

        teacherRepository.saveAndFlush(teacher);

        return teacher + "'s data has been updated!!!";
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public String deleteById(String teacherId) {
        Teacher teacher = teacherRepository.findById(teacherId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Teacher's ID was not found!!!"));
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
