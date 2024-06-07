//package com.example.demo;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.PageRequest;
//import org.springframework.data.domain.Pageable;
//import org.springframework.stereotype.Component;
//import org.springframework.stereotype.Repository;
//import org.springframework.stereotype.Service;
//
//@Service
//public class BoardService {
//
//    private BoardRepository boardRepository;
//    
//    @Autowired
//    public BoardService(BoardRepository boardRepository) {
//        this.boardRepository = boardRepository;
//    }
//    
//    public Page<boardEntity> getAllBoards(Pageable pageable) {
//        return boardRepository.findAll(pageable);
//    }
//
//    public boardEntity getBoardById(Integer num) {
//        return boardRepository.findById(num).orElse(null);
//    }
//
//    public boardEntity saveBoard(boardEntity board) {
//        return boardRepository.save(board);
//    }
//
//    public void deleteBoard(Integer num) {
//        boardRepository.deleteById(num);
//    }
//    
//
////    public Page<boardEntity> getPosts(int page, int size) {
////        Pageable pageable = PageRequest.of(page, size);
////        return boardRepository.findAll(pageable);
////    }
//	
//}
