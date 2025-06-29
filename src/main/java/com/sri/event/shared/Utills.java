package com.sri.event.shared;

import java.security.SecureRandom;
import java.util.Random;

import org.springframework.stereotype.Component;

@Component
public class Utills {

	private final Random RANDOM=new SecureRandom();
	private final String ALPHABET="abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
	
	public String generateUserId(int length) {
		return generateRandomString(length);
	}
	
	public String generateOwnerId(int length) {
		return generateRandomString(length);
	}
	
	private String generateRandomString(int length) {
		StringBuilder returnValue=new StringBuilder();
		for(int i=0;i<length;i++) {
			returnValue.append(ALPHABET.charAt(RANDOM.nextInt(ALPHABET.length())));
		}
		return  returnValue.toString() ;
	}
}
