package com.example.demo;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

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
        		boardEntity board = new boardEntity();        	
        		String title = Objects.toString(userMap.get("title"), "");
        		String content = Objects.toString(userMap.get("content"), "");
        		String userId = Objects.toString(userInfo.get("userEmail"), "");
        		String userNm = Objects.toString(userInfo.get("userName"),  "");
        		
        		board.setBoard(title, content, userId ,userNm, LocalDateTime.now() );
        		boardEntity be = boardRepository.save(board);
        		
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
	
	
	@RequestMapping(value = "/board.do") //Pageable pageable, @RequestParam(defaultValue = "0") int page,  @RequestParam(defaultValue = "10") int size
	public String board(Model model,Pageable pageable, @RequestParam(defaultValue = "0") int page,  @RequestParam(defaultValue = "10") int size) {

		//List<boardEntity> boards = boardService.getBoards(page, size, "num");
		//model.addAttribute("boards", boards);
		Page<boardEntity> boardList = boardService.getBoardList(page, size, "num");
		model.addAttribute("boardList", boardList);
		model.addAttribute("totalPages", boardList.getTotalPages());
		model.addAttribute("currentPage", boardList.getNumber());
		model.addAttribute("size", boardList.getSize());
		model.addAttribute("hasNext", boardList.hasNext());
		model.addAttribute("hasPrevious", boardList.hasPrevious());
		model.addAttribute("nowPage", page);
		
		
		return "board";
	}
	
	@RequestMapping(value = "/boardView.do")
	public String board(Model model) {
		
		return "boardView";
	}
	
}
