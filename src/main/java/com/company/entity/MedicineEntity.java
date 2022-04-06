package com.company.entity;

import com.company.enums.MedicineKategory;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Setter
@Getter
@Entity
@Table(name = "Medicine")
public class MedicineEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    private String name;

    @Column
    private Double price;

    @Enumerated(EnumType.STRING)
    @Column
    private MedicineKategory medicineKategory;

    @Column
    private String description;

    @Column
    private Integer amount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private CompanyEntity company;
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn
//    private AttachEntity attach;

    @Column
    private String code;

    @Column(name = "manufacture_date")
    private LocalDate manufacture_date;

    @Column(name = "expiration_date")
    private LocalDate expiration_date;

    @Column(name = "created_date")
    private LocalDateTime createdDate;
}

