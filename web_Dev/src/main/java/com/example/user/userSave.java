package com.example.user;

import java.lang.reflect.Member;
import java.time.LocalDateTime;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;

public class userSave {
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpatest");
	EntityManager em = emf.createEntityManager();
	
	EntityTransaction tx = em.getTransaction();
	tx.begin();
	
		try {
			userEntty user = new userEntty();
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
