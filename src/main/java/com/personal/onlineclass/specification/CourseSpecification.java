package com.personal.onlineclass.specification;

import com.personal.onlineclass.dto.request.SearchCourseRequest;
import com.personal.onlineclass.entity.Course;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class CourseSpecification {
    public static Specification<Course> getSpecification(SearchCourseRequest courseRequest){
        return (root, query, criteriaBuilder) -> {

            List<Predicate> predicates = new ArrayList<>();
            if (courseRequest.getTitle() != null){
                Predicate titlePredicate = criteriaBuilder.like(criteriaBuilder.lower(root.get("title")), "%" + courseRequest.getTitle().toLowerCase() + "%");
                predicates.add(titlePredicate);
            }

            return query.where(predicates.toArray(new Predicate[]{})).getRestriction();
        };
    }
}
