package com.company.entity;

import com.company.enums.ProfileRole;
import com.company.enums.ProfileStatus;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;


@Setter
@Getter
@Entity
@Table(name = "profile")
public class ProfileEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    private String name;

    @Column
    private String surname;

    @Column
    private String login;

    @Column
    private String pswd;

    @Column(unique = true)
    private String email;

    @Column
    private String phone;

    @Column(name = "last_active_date")
    private LocalDateTime lastActiveDate;

    @ManyToOne
    @JoinColumn(name = "apteka_id")
    private PharmacyEntity apteka;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    private ProfileRole role;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private ProfileStatus status;

    @Column(name = "created_date")
    private LocalDateTime createdDate;


}
