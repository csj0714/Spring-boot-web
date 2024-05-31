package com.example.test.user;

import java.util.Date;

import org.hibernate.annotations.ColumnDefault;
import org.springframework.web.multipart.MultipartFile;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
	
	@Column(name="hobby",nullable = true)
	private String hobby;
	
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
	

	
	
	//날짜타입의 기본은 타임스템프
	@Temporal(TemporalType.TIMESTAMP)//연월일 시분초 밀리초
//	@Temporal(TemporalType.DATE)//연월일
	@Column(name="regdate",insertable = false) //입력시 sysdate으로 처리,수정시 널값반영(이럴때는 new Date()처리해준다.)
	@ColumnDefault(value="sysdate")
	private Date regdate;
}
