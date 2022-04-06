package com.company.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "attach")
public class AttachEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "url")
    private String url;
    @Column(name = "path")
    private String path;
    @Column(name = "size")
    private long size;
    @Column(name = "type")
    private String type;
    @Column(name = "token")
    private String token;
    @Column(name = "create_Date")
    private LocalDateTime createdDate;

}
