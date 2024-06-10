package com.example.test.user;

import java.util.Date;
import java.util.List;

import org.hibernate.annotations.ColumnDefault;
import org.springframework.web.multipart.MultipartFile;

import com.example.test.meeting.MeetingDTO;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.persistence.UniqueConstraint;

import lombok.Data;

//@Entity : 자바의 객체와 DB테이블을 매칭시켜주는 기능
//@Table : DB에 테이블을 정의 해준다.

@Data
@Entity
@Table(name="users",uniqueConstraints = {
		@UniqueConstraint(columnNames = {"username"})
})
public class UserDTO {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="num")//컬럼이름 설정
	private int num;
	
	@Column(name="realname",nullable = false)
	private String realname;
	
	@Column(name="username",nullable = false)
	private String username;
	
	@Column(name="pw",nullable = false)
	private String pw;
	
	@Column(name="kakaoID",nullable = true)
	private String kakaoID;
	
	@Column(name="age",nullable = true)
	private Integer age;
	
	@Column(name="gender",nullable = true)
	private String gender;
	
	@Column(name="school",nullable = true)
	private String school;
	
	@Column(name="tel",nullable = true)
	private String tel;
	
	@Column(name="introduce",nullable = true)
	private String introduce;
	
	@Column(name="form",nullable = true)
	private String form;
	
	@Column(name="save_name",nullable = true)
	private String save_name;
	
	@Column(name="file_path",nullable = true)
	private String file_path;
	
	
	@Temporal(TemporalType.TIMESTAMP)
    @Column(name = "regdate", updatable = false)
    private Date regdate;
	
	@PrePersist
    protected void onCreate() {
        regdate = new Date();
    }
}
