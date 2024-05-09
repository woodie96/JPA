package com.example.demo.controller;

import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.commonUtil;
import com.example.demo.userEntity;
import com.example.demo.userRepository;
import com.example.demo.userService;
import com.google.gson.Gson;

import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;


@Controller
public class mainController {
	
	@Autowired
	private userService userService;

	@RequestMapping(value = "/main.do , /")
	public String main() {
		return "index";
	}
	
	@RequestMapping(value = "/board.do")
	public String board() {
		return "board";
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
				userEntity user = userService.findUserById(userEmail);
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
					session.setAttribute(user.getEmail()+"_loginSession", loginMap);
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
