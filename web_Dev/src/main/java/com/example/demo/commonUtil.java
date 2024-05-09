package com.example.demo;

import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.util.Base64;

import org.springframework.stereotype.Service;

import com.google.common.hash.Hashing;

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

}
