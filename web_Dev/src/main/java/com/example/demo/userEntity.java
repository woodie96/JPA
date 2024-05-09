package com.example.demo;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity //DB 테이블과 매핑 대상이라고 알리는 역할
@Table(name="user") //user 테이블과 매핑 (클래스 명과 테이블 명이 동일하다면 생략 가능)
public class userEntity {
	
	@Id // 식별자에 대응 > 기본키일때 지정하는 듯 함 *별도의 지정이 없을 땐 컬럼 = 변수명 같은걸 Id로 인식
	private String email = "";
	private String password = "";
	private String name = "";
	@Column(name="reg_dt")
	private LocalDateTime regDt;
	private String salt;
	
	
	public String getEmail() {
		return email;
	}
	
	
	public String getPw() {
		return password;
	}
	
	public String getSalt() {
		return salt;
	}
	
	public String getName() {
		return name;
	}
	
	public void insertUser(String email, String pw, String name, LocalDateTime regDt, String salt) {
		this.email = email;
		this.password = pw;
		this.name = name;
		this.regDt = regDt;
		this.salt = salt;
	}
	
	public LocalDateTime getRegDt() {
		return regDt;
	}
	
	public boolean checkPassword(String password){
	    return this.password.equals(password);
	}
	

	
	
	
}
