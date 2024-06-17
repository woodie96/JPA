package com.example.demo;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity //DB 테이블과 매핑 대상이라고 알리는 역할
@Table(name="board") // 테이블과 매핑 (클래스 명과 테이블 명이 동일하다면 생략 가능)
public class boardEntity {
	
	@Id // 식별자에 대응 > 기본키일때 지정하는 듯 함 *별도의 지정이 없을 땐 컬럼 = 변수명 같은걸 Id로 인식
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer num;
	private String title = "";
	private String content = "";
	@Column(name="reg_id")
	private String regId = "";
	@Column(name="reg_nm")
	private String regNm = "";
	@Column(name="reg_dt")
	private LocalDateTime regDt;
	
	
	
	public Integer getNum() {
		return num;
	}
	
	public String getTitle() {
		return title;
	}
	
	public String getContent() {
		return content;
	}
	
	public String getRegId() {
		return regId;
	}
	
	public String getRegNm() {
		return regNm;
	}
	
	public LocalDateTime getRegDt() {
		return regDt;
	}
	
	public void setNum(Integer num) {
		this.num=num;
	}
	
	public void setTitle(String title) {
		this.title=title;
	}
	
	public void setContent(String content) {
		this.content=content;
	}
	public void setRegId(String regId) {
		this.regId=regId;
	}
	public void setRegNm(String regNm) {
		this.regNm=regNm;
	}
	public void setRegDt(LocalDateTime regDt) {
		this.regDt=regDt;
	}
	
	
	public void setBoard(String title, String content, String regId, String regNm, LocalDateTime regDt) {
		this.title = title;
		this.content = content;
		this.regId = regId;
		this.regNm = regNm;
		this.regDt = regDt;
	}
	
	public void updateBoard(String title, String content) {
		this.title = title;
		this.content = content;
	}
	


	
	
	
}
