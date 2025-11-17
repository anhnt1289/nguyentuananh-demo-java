package com.base.common.model;

import com.base.common.constant.UserRoleEnum;
import com.base.common.constant.UserStatusEnum;
import com.base.common.util.AuthProvider;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author : AnhNT
 * @since : 10/11/2021, Wed
 */
@Entity
@Table(name = "users", uniqueConstraints = {
        @UniqueConstraint(columnNames = "email")
})
@Setter
@Getter
public class User extends BaseEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "\"id\"")
    private Long id;

    @Column(name = "\"name\"", nullable = false)
    private String name;

    @Email
    @Column(name = "\"email\"", nullable = false)
    private String email;

    @Column(name = "\"image_url\"")
    private String imageUrl;

    @Column(name = "\"email_verified\"", nullable = false)
    private Boolean emailVerified = false;

    @JsonIgnore
    @Column(name = "\"password\"")
    private String password;

    @Transient
    private String passwordConfirm;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "\"provider\"")
    private AuthProvider provider;

    @Column(name = "\"provider_id\"")
    private String providerId;

    // 0: administrator
    // 1: admin
    // 2: user
    @Column(name = "\"role_id\"", nullable = false)
    private Integer roleId = UserRoleEnum.USER.getId();
}
