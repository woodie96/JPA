package com.example.demo;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.data.repository.query.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

@Service
public class BoardService {

    @Autowired
    private BoardRepository boardRepository;
    
    public Page<boardEntity> getBoardList(int page, int size, String pkey) {
    	
    	Pageable pageable = PageRequest.of(page, size, Sort.by(pkey).descending());

        return boardRepository.findAll(pageable);
    }
    
    public Optional<boardEntity> getBoardById(int num) {
        return boardRepository.findById(num);
    }
    
    public Page<boardEntity> search(String title, String content) {
    	//검색시 기본 0페이지부터 시작하도록 설정
    	Pageable pageable = PageRequest.of(0, 10, Sort.by("num").descending());
    	Page<boardEntity> boardList = boardRepository.findByTitleContainingOrContentContaining(title, content, pageable);
    	return boardList;
    }
}
