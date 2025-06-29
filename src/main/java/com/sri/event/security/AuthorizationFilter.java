package com.sri.event.security;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import javax.crypto.SecretKey;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import com.sri.event.SpringApplicationContext;
import com.sri.event.Entity.OwnerEntity;
import com.sri.event.Entity.UserEntity;
import com.sri.event.repo.OwnerRepo;
import com.sri.event.repo.UserRepo;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class AuthorizationFilter extends BasicAuthenticationFilter{
	
	UserRepo userRepo;
	
	OwnerRepo ownerRepo;
	
	public AuthorizationFilter(AuthenticationManager authenticationManager,UserRepo userRepo,OwnerRepo ownerRepo) {
		super(authenticationManager);
		this.userRepo=userRepo;
		this.ownerRepo=ownerRepo;
	}
	
	@Override
	protected void doFilterInternal(
			HttpServletRequest req, HttpServletResponse res, FilterChain chain)
			throws ServletException, IOException{
		String authorizationHeader=req.getHeader(SecurityConstants.HEADER_STRING);
		if(authorizationHeader==null || !authorizationHeader.startsWith(SecurityConstants.TOKEN_PREFIX)) {
			chain.doFilter(req, res);
			return;
		}
		authorizationHeader=authorizationHeader.replace(SecurityConstants.TOKEN_PREFIX,"").trim();
		UsernamePasswordAuthenticationToken authorization=getAuthentication(authorizationHeader);
		SecurityContextHolder.getContext().setAuthentication(authorization);
		chain.doFilter(req, res);
	}
	
	private UsernamePasswordAuthenticationToken getAuthentication(String token) {
		AppProperties appProperties=(AppProperties)SpringApplicationContext.getBean("AppProperties");
		String commonKey=appProperties.getTokenSecret();
		
		if(commonKey==null)return null;
		
		SecretKey key=Keys.hmacShaKeyFor(commonKey.getBytes(StandardCharsets.UTF_8));
		
		Claims claims=Jwts.parser()
		.verifyWith(key)
		.build()
		.parseSignedClaims(token)
		.getPayload();
		
		
		
		String userId=claims.getSubject();
		String ownerId=claims.getSubject();
		
		// Check in userRepo
		UserEntity user = userRepo.findByUserId(userId);
        if (user != null) {
            UserPrinciple principal = new UserPrinciple(user);
            return new UsernamePasswordAuthenticationToken(principal, null, principal.getAuthorities());
        }

        // Check in ownerRepo
        OwnerEntity owner = ownerRepo.findByOwnerId(ownerId);
        if (owner != null) {
            OwnerPrinciple principal = new OwnerPrinciple(owner);
            return new UsernamePasswordAuthenticationToken(principal, null, principal.getAuthorities());
        }
    return null;
		
	}
	

}
