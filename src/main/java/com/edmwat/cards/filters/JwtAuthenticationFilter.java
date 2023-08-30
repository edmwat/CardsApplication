package com.edmwat.cards.filters;

import com.edmwat.cards.dto.ErrorResponse;
import com.edmwat.cards.exceptions.InvalidTokenExption;
import com.edmwat.cards.services.UserService;
import com.edmwat.cards.services.TokenService;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
import java.util.Objects;

@Component 
@AllArgsConstructor 
@Slf4j 
public class JwtAuthenticationFilter extends OncePerRequestFilter{
	private final TokenService tokenFactory;

	private final UserService userService;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		if(request.getServletPath().equals("/authenticate")) {
			filterChain.doFilter(request, response);
		}else {
			try {
				final String authorizationHeader = request.getHeader("Authorization");
				String username ="";
								
					if( authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
						String jwt = authorizationHeader.substring(7);
						username = tokenFactory.extractUsername(jwt);
						
					}else {
						throw new InvalidTokenExption("The Token Header is Null",401);
					}
					if(Objects.nonNull(username) && !username.isEmpty() && SecurityContextHolder.getContext().getAuthentication() == null){


						UserDetails userDetails = userService.loadUserByUsername(username);
						UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
								new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());

						usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource());
						SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
					}	
				
				filterChain.doFilter(request,response);
			}catch(RuntimeException e) {
				log.info(e.getMessage());
				ErrorResponse er = new ErrorResponse();
				er.setMessage(e.getMessage());				
				//er.setErrorCode(401);
				response.setStatus(401);
				response.getWriter().write(convertObjectToJson(er));
			}
		}
		
	}
	public String convertObjectToJson(Object object) throws JsonProcessingException {
        if (object == null) {
            return null;
        }
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(object);
    }
}
