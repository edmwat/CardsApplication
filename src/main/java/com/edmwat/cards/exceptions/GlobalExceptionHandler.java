package com.edmwat.cards.exceptions;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice 
@Slf4j 
public class GlobalExceptionHandler {

	@ExceptionHandler(value = AuthenticationException.class)
    public ResponseEntity<?> unAuthenticatedAccess(AuthenticationException unauthenticated) {
		log.error("UnAuthorized Access");
        return new ResponseEntity<Object>("Authentication Required", HttpStatus.UNAUTHORIZED);
    }
	
	@ExceptionHandler(value = InvalidTokenExption.class)
    public ResponseEntity<?> invalidTokenExption(InvalidTokenExption invalidToken) {
		log.error("invalid token____________________________"); 
        return new ResponseEntity<Object>("Token Not Valid", HttpStatus.UNAUTHORIZED);
    }
}
