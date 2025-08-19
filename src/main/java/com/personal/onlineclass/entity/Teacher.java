package com.personal.onlineclass.entity;

import com.personal.onlineclass.constant.ConstantTable;
import com.personal.onlineclass.constant.Field;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name = ConstantTable.TEACHER)
public class Teacher {

    @Id
    @GeneratedValue(generator = "custom-id")
    @GenericGenerator(name = "custom-id", strategy = "com.personal.onlineclass.utils.TeacherCustomId")
    @Column(name = "teacher_id")
    private String teacherId;

    @Column(name = "teacher_name", unique = true)
    private String teacherName;

    @Column(name = "email", unique = true)
    private String email;

    @Column(name = "contact_number", unique = true)
    private String contactNumber;

    @Column(name = "field")
    @Enumerated(EnumType.STRING)
    private Field field;
}
