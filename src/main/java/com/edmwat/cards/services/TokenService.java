package com.edmwat.cards.services;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

@Service
@Slf4j 
public class TokenService {
	
	final Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());
	final JWTVerifier verifier = JWT.require(algorithm).build();
	
	public String createToken(Authentication authentication) {
		
		String access_token = JWT.create()
				.withSubject(authentication.getName())
				.withExpiresAt(new Date(System.currentTimeMillis() + (60 *60 * 1000)))
				.withClaim("roles", authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
				.sign(algorithm);
		
		return access_token;
	}

	public DecodedJWT decodeToken(String token)  {
		DecodedJWT decodedJwt = null;
		try {
			decodedJwt = verifier.verify(token);	
			//return decodedJwt;	
		}catch(Exception e) {
			log.error(e.getMessage());
		}
		return decodedJwt;		
	}
	
	public String extractUsername(String token) {
		String subject = "";
		try{
			subject = decodeToken(token).getSubject();
		}catch(Exception e) {
			log.error(e.getMessage());
			throw new RuntimeException("Invalid Token!!");
		}
		return subject;
	}
	public Boolean validateToken(String token, UserDetails userDetails) {
		final String username = extractUsername(token);
		return (username.equals(userDetails.getUsername()) && !isExpired(token));
	}
	public boolean isExpired(String token) {
		
		DecodedJWT decodedJwt = null;
		try {
			decodedJwt = verifier.verify(token);	
		}catch(Exception e) {
			log.error(e.getMessage());
		}
		return decodedJwt != null ? false : true; 
	}
	
	public Collection<SimpleGrantedAuthority> extractRoles(){
		Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
		
		return authorities;
	}
}
