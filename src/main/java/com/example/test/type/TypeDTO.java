package com.example.test.type;

import jakarta.persistence.*;

import java.util.Date;

public class TypeDTO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="num")//컬럼이름 설정
    private int num;

    @Column(name="username",nullable = false)
    private String username;

    @Column(name="realName",nullable = false)
    private String realName;

    @Column(name="nickname",nullable = false)
    private String nickname;

    @Column(name="ageRange",nullable = false)
    private String ageRange;

    @Column(name="job",nullable = false)
    private String job;

    @Column(name="region",nullable = false)
    private String region;

    @Column(name="department",nullable = false)
    private String department;

    @Column(name="smoking",nullable = false)
    private String smoking;

    @Column(name="form",nullable = false)
    private String form;

    @Column(name="army",nullable = false)
    private String army;

    @Column(name="tall",nullable = false)
    private String tall;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "regdate", updatable = false)
    private Date regdate;

    @PrePersist
    protected void onCreate() {
        regdate = new Date();
    }
}
