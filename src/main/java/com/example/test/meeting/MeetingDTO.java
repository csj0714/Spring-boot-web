package com.example.test.meeting;

import java.util.Date;
import java.util.List;


import com.example.test.user.UserDTO;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Data;

@Data
@Entity
@Table(name="meetings")
public class MeetingDTO {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="num")//컬럼이름 설정
	private int num;
	
	@Column(name="organizerNum",nullable = false)
	private int organizerNum;
	
	@Column(name="organizerNickname",nullable = false)
	private String organizerNickname;
	
	@Column(name="many",nullable = false)
	private Integer many;
	
	@Column(name="school",nullable = false)
	private String school;
	
	@Column(name="average",nullable = false)
	private Integer average;
	
	@Column(name="introduce",nullable = false)
	private String introduce;
	
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "regdate", updatable = false)
    private Date regdate;

    @PrePersist
    protected void onCreate() {
        regdate = new Date();
    }
}
