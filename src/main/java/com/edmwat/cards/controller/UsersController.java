package com.edmwat.cards.controller;

import com.edmwat.cards.dto.AuthenticationResponse;
import com.edmwat.cards.services.UserServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController 
@AllArgsConstructor 
public class UsersController {
	
	private final UserServiceImpl userService;

	@PostMapping("/authenticate")
	public ResponseEntity<?> getToken(Authentication authentication){

		AuthenticationResponse response = new AuthenticationResponse(userService.generateToken(authentication));
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

 
}
