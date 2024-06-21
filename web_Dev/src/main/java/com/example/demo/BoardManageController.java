package com.example.demo;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Controller
public class BoardManageController {
	private BoardRepository boardRepository;
	  
	@Autowired 
	public BoardManageController(BoardRepository boardRepository) {
		this.boardRepository = boardRepository; 
	}
	 
	@Autowired
	private BoardService boardService;
	 
	@RequestMapping(value = "/insertBoardPage.do")
	public String insertBoardPage() {
		return "insertBoard";
	}
	
	@RequestMapping(value = "/insertBoard.do")
	@ResponseBody
	public void insertBoard(HttpServletResponse response, HttpServletRequest request, @RequestParam HashMap<String, Object> userMap) {
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
        Gson gson = new Gson();
        HashMap<String, Object> resultMap = new HashMap<String, Object>();
        String json = "";
        
        try {
        	HttpSession session = request.getSession();
        	Map userInfo = (Map)session.getAttribute(session.getId()+"_loginSession");
        	
        	if(userInfo == null) {
        		resultMap.put("result", "NL");
        	} else {
        		BoardEntity board = new BoardEntity();        	
        		String title = Objects.toString(userMap.get("title"), "");
        		String content = Objects.toString(userMap.get("content"), "");
        		String userId = Objects.toString(userInfo.get("userEmail"), "");
        		String userNm = Objects.toString(userInfo.get("userName"),  "");
        		
        		board.setBoard(title, content, userId ,userNm, LocalDateTime.now() );
        		BoardEntity be = boardRepository.save(board);
        		
        		if(be == null) {
        			resultMap.put("result", "N");
        		} else {
        			if(be.getNum() > 0) {
        				resultMap.put("result", "Y");
        			}
        		}
        		
        	}
        	json = gson.toJson(resultMap);
			response.getWriter().write(json); // JSON 데이터 응답
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	@RequestMapping(value = "/board.do")
	public String board(HttpServletRequest request, HttpServletResponse response, Model model,Pageable pageable, @RequestParam(defaultValue = "0") int page,  @RequestParam(defaultValue = "10") int size, @RequestParam HashMap<String, Object> boardMap ) {

		HttpSession session = request.getSession();
		Map userInfo = commonUtil.getUserSession(request);
		if(userInfo == null) {
			return commonUtil.alertException(response,"로그인 후 다시 시도해주세요.","/");
		}
		userInfo.put("time", session.getMaxInactiveInterval());
		model.addAttribute("userInfo", userInfo);
		Page<BoardEntity> boardList;
		if(Objects.toString(boardMap.get("searchValue"), "").equals("")) {			
			boardList = boardService.getPostsWithRowNumber(page, size, "num");
		} else {
			boardList = boardService.search(Objects.toString(boardMap.get("searchValue"), ""));
		}
		
		model.addAttribute("boardList", boardList);
		model.addAttribute("totalPages", boardList.getTotalPages());
		model.addAttribute("currentPage", boardList.getNumber());
		model.addAttribute("size", boardList.getSize());
		model.addAttribute("hasNext", boardList.hasNext());
		model.addAttribute("hasPrevious", boardList.hasPrevious());
		model.addAttribute("nowPage", page);
		model.addAttribute("map", boardMap);
		
		
		return "board";
	}
	
	@RequestMapping(value = "/boardView.do")
	public String board(HttpServletResponse response, HttpServletRequest request, Model model, @RequestParam HashMap<String, Object> map) {
		int key = Integer.parseInt(Objects.toString(map.get("num"), "0"));
		
		Optional<BoardEntity> board = boardRepository.findById(key);
		
		HttpSession session = request.getSession();
		Map userInfo = commonUtil.getUserSession(request);
		
		if(board.isEmpty()) {
			return "redirect : /board.do";
		} else {
			if(userInfo != null) {
				if (board.get().getRegId().equals(userInfo.get("userEmail"))) {
					model.addAttribute("boardAuth", "Y");
				}				
			}
		}
		
		model.addAttribute("board", board.get());
		
		return "boardView";
	}
	
	
	@RequestMapping(value = "/updateBoard.do")
	@ResponseBody
	public void updateBoard(HttpServletResponse response, HttpServletRequest request, @RequestParam HashMap<String, Object> boardMap) {
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
        Gson gson = new Gson();
        HashMap<String, Object> resultMap = new HashMap<String, Object>();
        String json = "";
        
        try {
        	HttpSession session = request.getSession();
        	Map userInfo = (Map)session.getAttribute(session.getId()+"_loginSession");
        	
        	if(userInfo == null) {
        		resultMap.put("result", "NL");
        	} else {
        		int num = Integer.parseInt(Objects.toString(boardMap.get("num"), "0"));
        		BoardEntity entity = boardRepository.findById(num).orElseThrow();
        		if(entity == null) {
        			resultMap.put("result", "N");
        		} else {
        			if(entity.getNum() > 0) {
        				entity.updateBoard(Objects.toString(boardMap.get("title"), ""), Objects.toString(boardMap.get("content"), ""));
        				boardRepository.save(entity);
        				resultMap.put("result", "Y");
        			}
        		}
        		
        	}
        	
        	json = gson.toJson(resultMap);
			response.getWriter().write(json); // JSON 데이터 응답
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@RequestMapping(value = "/deleteBoard.do")
	public String deleteBoard(HttpServletResponse response, HttpServletRequest request, Model model, @RequestParam HashMap<String, Object> map) {
		int num = Integer.parseInt(Objects.toString(map.get("num"), "0"));
		
		BoardEntity entity = boardRepository.findById(num).orElseThrow();
		
		HttpSession session = request.getSession();
		Map userInfo = commonUtil.getUserSession(request);
		
		if(null == entity) {
			return "redirect : /board.do";
		} else {
			if(userInfo != null) {
				if (entity.getRegId().equals(userInfo.get("userEmail"))) {
					boardRepository.delete(entity);
				}				
			}
		}
		
		return "redirect:/board.do";
	}
	
}
