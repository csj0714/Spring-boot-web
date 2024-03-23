package com.example.test.board;

import java.util.Date;

import org.hibernate.annotations.ColumnDefault;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name="BoardDTO")
public class BoardDTO {

	@Id  //pk설정
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_board_jpa")
	@SequenceGenerator(sequenceName = "seq_board_jpa",  allocationSize = 1,   name = "seq_board_jpa")// 시퀀스 자동생성된다.
	@Column(name="num")//컬럼이름 설정
	private int num;
	
	@Column(name="title",nullable = false)
	private String title;
	
	@Column(name="content",nullable = false)
	private String content;
	
	@Column(name="writer",nullable = false)
	private String writer;
	
	
	//날짜타입의 기본은 타임스템프
	@Temporal(TemporalType.TIMESTAMP)//연월일 시분초 밀리초
//	@Temporal(TemporalType.DATE)//연월일
	@Column(name="wdate",insertable = false) //입력시 sysdate으로 처리,수정시 널값반영(이럴때는 new Date()처리해준다.)
	@ColumnDefault(value="sysdate")
	private Date wdate;
	
}
