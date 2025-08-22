package com.personal.onlineclass.utils;

import jakarta.persistence.Id;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;

public class CourseCustomId implements IdentifierGenerator {
    @Override
    public Object generate(SharedSessionContractImplementor sharedSessionContractImplementor, Object o) {
        String prefix = "C";
        String query = "SELECT COALESCE(MAX(CAST(SUBSTRING(course_id, 2) AS INTEGER)), 0) FROM m_courses";
        Integer max = (Integer) sharedSessionContractImplementor.createNativeQuery(query).getSingleResult();
        int nextId = (max == null ? 1 : max + 1);
        return prefix + String.format("%03d", nextId);
    }
}
