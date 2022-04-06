package com.company.entity;

import com.company.enums.RegionStatus;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Setter
@Getter
@Entity
@Table(name = "region")
public class RegionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    private String district;

    @Enumerated(EnumType.STRING)
    @Column(name = "region", nullable = false)
    private RegionStatus region;

    @Column(name = "created_date")
    private LocalDateTime createdDate;


}
