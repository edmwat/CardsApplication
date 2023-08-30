package com.edmwat.cards.filters;

import com.edmwat.cards.dto.ErrorResponse;
import com.edmwat.cards.exceptions.InvalidTokenExption;
import com.edmwat.cards.services.UserService;
import com.edmwat.cards.services.TokenService;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Component 
@AllArgsConstructor 
@Slf4j 
public class JwtAuthenticationFilter extends OncePerRequestFilter{
	private final TokenService tokenFactory;

	private final UserService userService;
	@Autowired
	private ObjectMapper mapper;

	public String convertObjectToJson(Object object) throws JsonProcessingException {
        if (object == null) {
            return null;
        }
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(object);
    }

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws ServletException, IOException {
		final String requestTokenHeader = request.getHeader("Authorization");
		response.setContentType(MediaType.APPLICATION_JSON_VALUE);
		response.setContentType("UTF-8");

		Map<Object, String> errorDetails = new HashMap<>();
		String username = null;
		String jwtToken = null;
		// JWT Token is in the form "Bearer token". Remove Bearer word and get only the Token
		if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
			jwtToken = requestTokenHeader.substring(7);
			// PrintWriter writer = response.getWriter();
			try {
				username = username = tokenFactory.extractUsername(jwtToken);
			} catch (IllegalArgumentException e) {
				System.out.println("Unable to get JWT Token");
				errorDetails.put("message", "Unable to get JWT Token");
				response.setStatus(HttpStatus.UNAUTHORIZED.value());
				mapper.writeValue(response.getWriter(), errorDetails);
				//writer.write("Unable to get JWT Token");
			} catch (ExpiredJwtException e) {
				errorDetails.put("message", "JWT token expired");
				response.setStatus(HttpStatus.UNAUTHORIZED.value());
				mapper.writeValue(response.getWriter(), errorDetails);
			}
		} else {
			logger.warn("JWT Token does not begin with Bearer String");
		}

		//Once we get the token validate it.
		if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

			UserDetails userDetails = userService.loadUserByUsername(username);

			// if token is valid configure Spring Security to manually set authentication
			if (tokenFactory.validateToken(jwtToken, userDetails)) {

				UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
						userDetails, null, userDetails.getAuthorities());
				usernamePasswordAuthenticationToken
						.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

				// After setting the Authentication in the context, we specify
				// that the current user is authenticated. So it passes the Spring Security Configurations successfully.
				SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
			}
		}
		chain.doFilter(request, response);
	}
}
