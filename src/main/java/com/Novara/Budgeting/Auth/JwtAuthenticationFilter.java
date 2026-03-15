package com.Novara.Budgeting.Auth;

import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
@Component
@AllArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
            
                String authHeader = request.getHeader("Authorization");
                String token = null;
                //String personRole = null;
                String personId = null;
                String personEmail = null;


                /*Extracting the JWT from the header */
                if(authHeader !=null && authHeader.startsWith("Bearer ")){
                    token = authHeader.substring(7);
                    try{
                        //personRole = jwtService.extractUserRole(token);
                        personId = jwtService.extractUserId(token);
                        personEmail= jwtService.extractUserEmail(token);


                    }catch(Exception e){
                         // Token is invalid so invoke the next filter chain dont let the rq pass
                    filterChain.doFilter(request, response);
                    return;
                    }
                }

                /*Validate the token and then set the security context */
                if(personId != null && personEmail !=null && SecurityContextHolder.getContext() == null){
                    Map<String, String> principal = Map.of("personId", personId, "personEmail", personEmail); 
                    if(jwtService.validateToken(token)){
                        //if the token is valid then create authentication object with role
                        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(principal,
                             null, 
                             Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"))
                    );

                    SecurityContextHolder.getContext().setAuthentication(authToken);
                    }
                }

                filterChain.doFilter(request, response);



    }
    
}
