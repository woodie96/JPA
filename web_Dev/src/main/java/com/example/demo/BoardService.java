package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

@Service
public class BoardService {

    @Autowired
    private BoardRepository boardRepository;

    public Slice<boardEntity> getBoards(int page, int size, String pkey) {
    	
    	Pageable pageable = PageRequest.of(page, size, Sort.by(pkey).descending());
    	
        return boardRepository.findAll(pageable);
    }
	
}
