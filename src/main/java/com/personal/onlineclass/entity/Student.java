package com.personal.onlineclass.entity;

import com.personal.onlineclass.constant.ConstantTable;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name = ConstantTable.STUDENT)
public class Student {
    @Id
    @GeneratedValue(generator = "custom-id")
    @GenericGenerator(name = "custom-id", strategy = "com.personal.onlineclass.utils.StudentCustomId")
    @Column(name = "student_id")
    private String studendId;

    @Column(name = "name", unique = true)
    private String studentName;

    @Column(name = "email", unique = true)
    private String email;

    @Column(name = "background", unique = true)
    private String background;

    @Column(name = "img")
    private String img;
}
