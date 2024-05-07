package com.example.demo;

import java.time.LocalDateTime;

import org.springframework.stereotype.Component;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;

public class userSave {
	public static void main(String[] args) {
		
	/* EntityManagerFactory
	 * 1. EntityManagerFactory 에서 EntityManager를 만듬 
	 * 2. persistence.xml 파일 내용 기반으로 생성
	 * 3. EntityManagerFactory는 여러 스레드가 동시에 접근해도 안전
	 * 4. 애플리케이션이 로딩되는 시점에 DB당 딱 하나만 생성해야 한다.
	 * */
	EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpatest");
	
	/* EntityManager
	 * 1. EntityManager > entity를 관리하는 클래스
	 * 2. entity를 저장하는 가상의 db
	 * 3. EntityManager 는 여러 스레드간에 공유하면 안됌(동시성 문제) / 트랜잭션이 수행된 후에는 반드시 닫고 DB 커넥션을 반환한다.
	 * 4. 실제 트랜잭션 단위가 수행될 때마다 생성된다.
	 * */
	EntityManager em = emf.createEntityManager();
	
	/* EntityTransaction
	 * 1. 데이터를 변경하는 모든 작업은 반드시 트랜잭션 안에서 이루어져야 한다.
	 * 2. tx.begin(); // 트랜잭션 시작
	 * 3. tx.commit(); // 트랜잭션 수행
	 * 4. tx.rollback(); // 작업에 문제 발생 시
	 * */
	EntityTransaction tx = em.getTransaction();
	tx.begin();
	
		try {
			userEntity user = new userEntity();
			user.insertUser("erohwa@gmail.com", "961206k!@", "김재우", LocalDateTime.now());
			System.out.println("###################################테스트진행중");
			em.persist(user);
			tx.commit();
			
		} catch (Exception e) {
			e.printStackTrace();
			tx.rollback();
		} finally {
			em.close();
		}
		emf.close();
	}

}
