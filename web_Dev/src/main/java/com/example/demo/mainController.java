package com.example.demo;

import java.io.Writer;
import java.net.URLEncoder;
import java.net.http.HttpResponse;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;

import jakarta.annotation.Resource;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;


@RestController
public class mainController {
	

//  @Autowired
//  private BoardService boardService;
	
  private userRepository userRepository;
  
  @Autowired
  public mainController(userRepository userRepository) {
    this.userRepository = userRepository;
  }


	@RequestMapping(value = "/main.do")
	public String main(HttpServletResponse response, HttpServletRequest request) {
		
    	HttpSession session = request.getSession();
    	Map userInfo = (Map)session.getAttribute(session.getId()+"_loginSession");
    	if(userInfo != null) {
    		return "redirect:/board.do";
    	}
		
		
		return "index";
	}
	
	@RequestMapping(value = "/board.do")
	public String board(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size, Model model, Pageable pageable) {
		
//		Page<boardEntity> postPage = boardService.getAllBoards(pageable);
//        model.addAttribute("postPage", postPage);
		
		return "board";
	}
	
	@RequestMapping(value = "/join.do")
	public String join() {
		return "join";
	}
	
	@RequestMapping(value = "/insertUser.do" , method = RequestMethod.POST)
	@ResponseBody
	public void saveUser(HttpServletResponse response, HttpServletRequest request, @RequestParam HashMap<String, Object> userMap) {
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
        Gson gson = new Gson();
        HashMap<String, Object> resultMap = new HashMap<String, Object>();
        String json = "";
		
		try {
			
			userEntity user = new userEntity();
			
			String email = Objects.toString(userMap.get("email"), "");
			String pwd = commonUtil.sha256Encode(Objects.toString(userMap.get("pwd"), ""));
			String name = Objects.toString(userMap.get("name"), "");
			String salt = commonUtil.salt();
			
			
			if(!userRepository.findById(email).isEmpty()) {
			    resultMap.put("result", "N");
			} else {
				user.insertUser(email, (pwd+salt), name, LocalDateTime.now(), salt);
				userRepository.save(user);
				resultMap.put("result", "Y");
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
			resultMap.put("result", "A");
		}
		
		try {
			json = gson.toJson(resultMap);
			response.getWriter().write(json); // JSON 데이터 응답
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@RequestMapping(value="/login.do", method = RequestMethod.POST)
	@ResponseBody
	public void login(HttpServletResponse response, HttpServletRequest request, @RequestParam HashMap<String, Object> map) {
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		String userEmail;
        Gson gson = new Gson();
        HashMap<String, Object> resultMap = new HashMap<String, Object>();
		try {
			
			String pwd = commonUtil.sha256Encode(Objects.toString(map.get("pwd"), ""));
			
			if(null != Objects.toString(map.get("email"), null) && !"".equals(Objects.toString(map.get("email"), ""))) {
				userEmail = Objects.toString(map.get("email"), "");
				userEntity user = userRepository.getById(userEmail);
				if(user == null) {
					resultMap.put("result", "N");
				} else {
					resultMap.put("result", "Y");
				}
				
				//해당 사용자 salt+pw 와 디비에 저장된 salt + 입력한 pw(암호화) 비교
				if( !user.getPw().equals(user.getSalt() + pwd) ) {
					resultMap.put("result", "N");
				} else {
					resultMap.put("result", "Y");
				}
				
				if( "Y".equals(resultMap.get("result")) ) {
					HttpSession session = request.getSession();
					HashMap loginMap = new HashMap();
					loginMap.put("userEmail", user.getEmail());
					loginMap.put("userName", user.getName());
					session.setAttribute(session.getId()+"_loginSession", loginMap);
					session.setMaxInactiveInterval(1800); // 30분
				}
				
			}
			
			String json = gson.toJson(resultMap);
		    response.getWriter().write(json); // JSON 데이터 응답
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	
}
