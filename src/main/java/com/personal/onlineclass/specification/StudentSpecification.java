package com.personal.onlineclass.specification;

import com.personal.onlineclass.dto.request.SearchStudentRequest;
import com.personal.onlineclass.entity.Student;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class StudentSpecification {
    public static Specification<Student> getSpecification (SearchStudentRequest studentRequest){
        return (root, query, criteriaBuilder) ->{
            List<Predicate> predicates = new ArrayList<>();
            if (studentRequest.getStudentName() != null){
                Predicate studentNamePredicate = criteriaBuilder.like(criteriaBuilder.lower(root.get("studentName")), "%" + studentRequest.getStudentName().toLowerCase() + "%");
                predicates.add(studentNamePredicate);
            }
            return query.where(predicates.toArray(new Predicate[]{})).getRestriction();
        };

    }
}
