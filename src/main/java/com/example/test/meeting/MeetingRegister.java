package com.example.test.meeting;


import java.util.Date;

import com.example.test.user.UserDTO;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Data;

@Entity
@Data
public class MeetingRegister {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="num")
    private Long num;

    @Column(name="meetingNum", nullable = false)
    private int meetingNum;

    @Column(name="userNum", nullable = false)
    private int userNum;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "application_date", nullable = false)
    private Date applicationDate;

    @Column(name="status", nullable = false)
    private String status;

    @PrePersist
    protected void onCreate() {
        applicationDate = new Date();
    }
}
