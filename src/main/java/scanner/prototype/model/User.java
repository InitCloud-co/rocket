package scanner.prototype.model;


import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import scanner.prototype.model.enums.UserState;
import scanner.prototype.model.enums.RoleType;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "USER")
public class User {

    @Id
    @Column(name = "USER_SEQ")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "USER_ID", unique = true)
    @NotNull
    @Size(max = 64)
    private String userId;

    @Column(name = "USERNAME")
    @NotNull
    @Size(max = 32)
    private String username;

    @Column(name = "PASSWORD", length = 256)
    @NotNull
    @Size(max = 256)
    private String password;

    @Column(name = "EMAIL", unique = true)
    @Size(max = 128)
    private String email;

    @Column(name = "CONTACT")
    @Size(max = 16)
    private String contact;

    @Column(name = "ROLE_TYPE", length = 8)
    @Enumerated(EnumType.STRING)
    @NotNull
    private RoleType roleType;

    @Column(name = "USER_STATE", length = 8)
    @Enumerated(EnumType.STRING)
    @NotNull
    private UserState userState;

    @Column(name = "CREATED_AT")
    @NotNull
    private LocalDateTime createdAt;

    @Column(name = "MODIFIED_AT")
    @NotNull
    private LocalDateTime modifiedAt;

    @Column(name = "LAST_LOGIN")
    private LocalDateTime lastLogin;
}
