package com.sri.event.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sri.event.SpringApplicationContext;
import com.sri.event.pojo.OwnerLoginRequestModel;
import com.sri.event.pojo.OwnerDto;
import com.sri.event.service.OwnerService;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.crypto.SecretKey;
import java.io.IOException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;

public class OwnerAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    public OwnerAuthenticationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
        setFilterProcessesUrl("/owner/login"); 
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest req, HttpServletResponse res)
            throws AuthenticationException {
        try {
            OwnerLoginRequestModel creds = new ObjectMapper().readValue(req.getInputStream(), OwnerLoginRequestModel.class);

            return getAuthenticationManager().authenticate(
                    new UsernamePasswordAuthenticationToken(creds.getEmail(), creds.getPassword(), new ArrayList<>()));
        } catch (IOException e) {
        	throw new RuntimeException("Failed to parse login request: " + e.getMessage(), e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
                                            Authentication authResult) throws IOException, ServletException {

        String ownerEmail = ((OwnerPrinciple) authResult.getPrincipal()).getUsername();

        OwnerService ownerService = (OwnerService) SpringApplicationContext.getBean("ownerServiceImpl");
        OwnerDto ownerDto = ownerService.getOwnerDetailsByEmail(ownerEmail);

        AppProperties appProperties = (AppProperties) SpringApplicationContext.getBean("AppProperties");
        String commonKey = appProperties.getTokenSecret();
        SecretKey secretKey = Keys.hmacShaKeyFor(commonKey.getBytes());

        String token = Jwts.builder()
                .subject(ownerDto.getOwnerId())
                .expiration(Date.from(Instant.now().plusMillis(Long.parseLong(appProperties.getTokenExpirationTime()))))
                .issuedAt(Date.from(Instant.now()))
                .signWith(secretKey)
                .compact();

        response.addHeader(SecurityConstants.HEADER_STRING, SecurityConstants.TOKEN_PREFIX + token);
        response.addHeader(SecurityConstants.OWNER_ID, ownerDto.getOwnerId());
        response.addHeader("Access-Control-Expose-Headers", SecurityConstants.OWNER_ID);
    }
}
