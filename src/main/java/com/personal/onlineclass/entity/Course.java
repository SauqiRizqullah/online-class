package com.personal.onlineclass.entity;

import com.personal.onlineclass.constant.ConstantTable;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import java.util.HashSet;
import java.util.Set;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name = ConstantTable.COURSE)
public class Course {
    @Id
    @GeneratedValue(generator = "custom-id")
    @GenericGenerator(name = "custom-id", strategy = "com.personal.onlineclass.utils.CourseCustomId")
    @Column(name = "course_id")
    private String courseId;

    @ManyToOne
    @JoinColumn(name = "teacher_id")
    private Teacher teacher;

    @Column(name = "title", unique = true)
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "price")
    private Long price;

    // ⬇⬇ TAMBAHAN BARU (bidirectional relasi)
    @ManyToMany(mappedBy = "courses")
    private Set<Student> students = new HashSet<>();
}
