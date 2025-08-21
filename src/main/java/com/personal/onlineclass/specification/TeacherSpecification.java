package com.personal.onlineclass.specification;


import com.personal.onlineclass.dto.request.SearchTeacherRequest;
import com.personal.onlineclass.entity.Teacher;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;


public class TeacherSpecification {
    public static Specification<Teacher> getSpecification (SearchTeacherRequest teacherRequest){
        return (root, query, criteriaBuilder) -> {

            List<Predicate> predicates = new ArrayList<>();
            if (teacherRequest.getTeacherName() != null){
                Predicate teacherNamePredicate = criteriaBuilder.like(criteriaBuilder.lower(root.get("teacherName")), "%" + teacherRequest.getTeacherName().toLowerCase() + "%");
                predicates.add(teacherNamePredicate);
            }
            return query.where(predicates.toArray(new Predicate[]{})).getRestriction();
        };
    }
}
