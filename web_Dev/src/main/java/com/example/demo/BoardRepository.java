package com.example.demo;


import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

@Repository
public interface BoardRepository extends JpaRepository<boardEntity, Integer>, JpaSpecificationExecutor<boardEntity> {
	
	Page<boardEntity> findByTitleContainingOrContentContaining(String title, String content, Pageable pageable);

}
