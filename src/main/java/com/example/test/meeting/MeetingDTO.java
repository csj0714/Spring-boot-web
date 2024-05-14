package com.example.test.meeting;

import java.util.Date;

import org.hibernate.annotations.ColumnDefault;

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

@Data
@Entity
@Table(name="meetings",uniqueConstraints = {
		@UniqueConstraint(columnNames = {"username"})
})
public class MeetingDTO {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="num")//컬럼이름 설정
	private int num;
	
	@Column(name="realname",nullable = false)
	private String realname;
	
	@Column(name="username",nullable = false)
	private String username;
	
	@Column(name="select_realname",nullable = false)
	private String select_realname;
	
	@Column(name="select_username",nullable = false)
	private String select_username;
	
	@Temporal(TemporalType.TIMESTAMP)//연월일 시분초 밀리초
//	@Temporal(TemporalType.DATE)//연월일
	@Column(name="regdate",insertable = false) //입력시 sysdate으로 처리,수정시 널값반영(이럴때는 new Date()처리해준다.)
	@ColumnDefault(value="sysdate")
	private Date regdate;
}
