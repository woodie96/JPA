package com.example.demo;


import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BoardRepository extends JpaRepository<BoardEntity, Integer> {
	
	//게시물 제목+내용으로 검색
	@Query(nativeQuery = true, value="SELECT * FROM (SELECT B.num, B.title, B.content, B.reg_id, B.reg_nm, B.reg_dt, row_number() OVER (ORDER BY B.num ASC) AS row_num  FROM Board B WHERE 1=1  AND (B.title like %:title%  or B.content like %:content%) ) AS tmp")
	Page<BoardEntity> findByTitleGet(@Param("title") String title, @Param("content") String content, Pageable pageable);

	
	//게시물 전체조회 및 게시물 번호 row_number() 처리
	@Query(nativeQuery = true, value = "SELECT * FROM (SELECT B.num, B.title, B.content, B.reg_id, B.reg_nm, B.reg_dt, row_number() OVER (ORDER BY B.num ASC) AS row_num  FROM Board B ) AS tmp")
    Page<BoardEntity> findAllByBoardEntity(Pageable pageable);
		
	

}
