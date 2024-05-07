package com.example.demo.controller;

import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.userEntity;
import com.example.demo.userRepository;
import com.example.demo.userService;
import com.google.gson.Gson;

import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


@Controller
public class mainController {
	
	@Autowired
	private userService userService;

	@RequestMapping(value = "/main.do , /")
	public String main() {
		return "index";
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
			System.out.println("### 입력 아이디: "+request.getParameter("email"));
			System.out.println("### 입력 패스워드: "+request.getParameter("pwd"));
			if(null != Objects.toString(map.get("email"), null) && !"".equals(Objects.toString(map.get("email"), ""))) {
				userEmail = Objects.toString(map.get("email"), "");
				userEntity user = userService.findUserById(userEmail);
				if(user == null) {
					resultMap.put("result", "N");
				} else {
					resultMap.put("result", "Y");
				}
				
				if(!user.getPw().equals(Objects.toString(map.get("pwd"), ""))) {
					resultMap.put("result", "N");
				} else {
					resultMap.put("result", "Y");
				}
				
			}
			
			String json = gson.toJson(resultMap);
		    response.getWriter().write(json); // JSON 데이터 응답
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	
}
