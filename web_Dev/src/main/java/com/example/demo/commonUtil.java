package com.example.demo;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.Map;
import java.util.StringTokenizer;

import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.google.common.hash.Hashing;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class commonUtil {
	
	private static final SecureRandom random = new SecureRandom();

	public static String sha256Encode(String text) {
		return Hashing.sha256()
			  .hashString(text, StandardCharsets.UTF_8).toString();
	}
	public static String salt() {
		byte[] salt = new byte[16];
		random.nextBytes(salt);
		return Base64.getEncoder().encodeToString(salt);
	}
	public static Map<String, Object> getUserSession(HttpServletRequest request) {
		HttpSession session = request.getSession();
		Map<String, Object> map = (Map)session.getAttribute(session.getId()+"_loginSession");
		return map;
	}
	public static String alertException(HttpServletResponse response, String message, String url) {
		response.setContentType("text/html; charset=UTF-8");
		response.setCharacterEncoding("utf-8");
		PrintWriter pwOut = null;

		try {
			pwOut = response.getWriter();
			if (message.length() > 100) { // 100자 이상의 메시지일 경우 한줄로 잘라낸다.
				StringTokenizer tokenMessage = new StringTokenizer(message, "\n");
				message = tokenMessage.nextToken();
			}
			pwOut.println("<!DOCTYPE html><html lang=\"ko\"><head><title>시스템안내</title><meta charset=\"utf-8\">");
			pwOut.println("<script language='javascript'>");
			pwOut.println("alert('" + message + "');");
			pwOut.println("location.href='" + url + "';");
			pwOut.println("</script>");
			pwOut.println("</head></html>");
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		} finally {
			pwOut.flush();
		}
		return null;
	}
	
}
