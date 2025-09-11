package com.personal.onlineclass.entity;

import com.personal.onlineclass.constant.ConstantTable;
import com.personal.onlineclass.constant.UserRole;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = ConstantTable.ROLE)
@Builder
public class Role {

    @Id
    @Column(name = "role_id")
    @GeneratedValue(generator = "custom-id")
    @GenericGenerator(name = "custom-id", strategy = "com.personal.onlineclass.utils.RoleCustomId")
    private String roleId;

    @Column(name = "user_role")
    @Enumerated(EnumType.STRING)
    private UserRole role;
}
