package com.example.demo;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class BoardService {

    @Autowired
    private BoardRepository boardRepository;
    
    public Page<BoardEntity> getBoardList(int page, int size, String pkey) {
    	
    	Pageable pageable = PageRequest.of(page, size, Sort.by(pkey).descending());

        return boardRepository.findAll(pageable);
    }
    
    public Page<BoardEntity> getPostsWithRowNumber(int page, int size , String pkey) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(pkey).descending());
        return boardRepository.findAllByBoardEntity(pageable);
    }
    
    
    
    public Optional<BoardEntity> getBoardById(int num) {
        return boardRepository.findById(num);
    }
    
    public Page<BoardEntity> search(String keyword) {
    	//검색시 기본 0페이지부터 시작하도록 설정
    	String title = keyword;
    	String content = keyword;
    	Pageable pageable = PageRequest.of(0, 10, Sort.by("num").descending());
    	Page<BoardEntity> boardList = boardRepository.findByTitleGet(title, content, pageable);
    	return boardList;
    }
}
