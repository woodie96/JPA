package com.example.user;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity //DB 테이블과 매핑 대상이라고 알리는 역할
@Table(name="user") //user 테이블과 매핑 
public class userEntty {
	
	@Id // 식별자에 대응 > 기본키일때 지정하는 듯 함 *별도의 지정이 없을 땐 컬럼 = 변수명 같은걸 Id로 인식
	private String email = "";
	private String password = "";
	private String name = "";
	@Column(name="reg_dt")
	private LocalDateTime regDt;
}
