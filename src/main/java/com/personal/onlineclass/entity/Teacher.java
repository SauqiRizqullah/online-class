package com.personal.onlineclass.entity;

import com.personal.onlineclass.constant.ConstantTable;
import com.personal.onlineclass.constant.Field;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name = ConstantTable.TEACHER)
public class Teacher implements UserDetails {

    @Id
    @GeneratedValue(generator = "custom-id")
    @GenericGenerator(name = "custom-id", strategy = "com.personal.onlineclass.utils.TeacherCustomId")
    @Column(name = "teacher_id")
    private String teacherId;

    @Column(name = "teacher_name", unique = true)
    private String teacherName;

    @Column(name = "email", unique = true)
    private String email;

    @Column(name = "username", nullable = false, unique = true)
    private String username;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "contact_number", unique = true)
    private String contactNumber;

    @Column(name = "field")
    @Enumerated(EnumType.STRING)
    private Field field;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Column(name = "is_enable")
    private Boolean isEnable;

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return isEnable;
    }
}
