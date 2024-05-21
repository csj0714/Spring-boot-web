package com.example.test.meeting;

import java.util.Date;

import org.hibernate.annotations.ColumnDefault;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.persistence.UniqueConstraint;
import lombok.Data;

@Data
@Entity
@Table(name="meetings")
public class MeetingDTO {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="num")//컬럼이름 설정
	private int num;
	
	@Column(name="applicantNum",nullable = false)
	private int applicantNum;
	
	@Column(name="applicantRealName",nullable = false)
	private String applicantRealName;
	
	@Column(name="applicantNickname",nullable = false)
	private String applicantNickname;
	
	@Column(name="receiverNum",nullable = false)
	private int receiverNum;
	
	@Column(name="receiverRealName",nullable = false)
	private String receiverRealName;
	
	@Column(name="receiverNickname",nullable = false)
	private String receiverNickname;
	
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "regdate", updatable = false)
    private Date regdate;

    @PrePersist
    protected void onCreate() {
        regdate = new Date();
    }
}
