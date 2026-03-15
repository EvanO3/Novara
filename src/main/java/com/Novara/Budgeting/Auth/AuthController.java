package com.Novara.Budgeting.Auth;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;


@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
public class AuthController {
        private final JwtService jwtService;

        /**
         * Validating the JWT token from the frontend, 
         * frontend will handle the supabase authentication then send the token to the backend
        */

        @PostMapping("/validate")
        public ResponseEntity<?> validatedToken(@RequestHeader("Authorization") String authHeader) {
            try{
                String token = authHeader.substring(7); // extracting the token from the header

                if(jwtService.validateToken(token)){
                    //String role = jwtService.extractUserRole(token);
                    String personId = jwtService.extractUserId(token);
                    String personEmail = jwtService.extractUserEmail(token);

                    Map<String, Object> response = new HashMap<>();

                    response.put("valid", true);
                    //response.put("role", role);
                    response.put("personId", personId);
                    response.put("personEmail", personEmail);
                    return ResponseEntity.ok(response);
                    
                }else{
                     return ResponseEntity.status(401).body(Map.of("valid", false, "message", "Invalid token"));
                }

            }catch(IllegalArgumentException e){
                return ResponseEntity.status(400).body(Map.of("valid", false, "message", e.getMessage()));
            }catch(Exception e){
                return ResponseEntity.status(401).body(Map.of("valid", false, "message", e.getMessage()));
            }
            
            
    
        }
        
}
