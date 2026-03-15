package com.Novara.Budgeting.Auth;


import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.function.Function;



import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.nimbusds.jose.crypto.ECDSAVerifier;
import com.nimbusds.jose.jwk.Curve;
import com.nimbusds.jose.jwk.ECKey;
import com.nimbusds.jose.util.Base64URL;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

import lombok.NoArgsConstructor;


@Service
@NoArgsConstructor
public class JwtService {

    @Value("${supabase.jwt.x}")
    private String ecX;

    @Value("${supabase.jwt.y}")
    private String ecY;

    /*Verification is done using X and Y to build a public key that is used to verify the jwt
    Supabase has a private key that they use to sign all JWT, therefore no one can spoof or forge the siging of the key
    but the verification of the key is public */

    private ECKey buildECKey() throws Exception{
        return new ECKey.Builder(Curve.P_256,
            new Base64URL(ecX),
            new Base64URL(ecY)
        ).build();
    }
    


    /*Validating the key*/
    private JWTClaimsSet extractAllClaims(String token){
        try{
            SignedJWT signedJWT = SignedJWT.parse(token);
            ECDSAVerifier verifier = new ECDSAVerifier(buildECKey());

            if(!signedJWT.verify(verifier)){
                throw new RuntimeException("Invalid token signature");
            }

                /*Check to see if the token has not expired yet */
                Date expiration = signedJWT.getJWTClaimsSet().getExpirationTime();
                if(expiration != null && expiration.before(new Date())){
                    throw new RuntimeException("Token has expired");
                }
                return signedJWT.getJWTClaimsSet();
         
            
        }catch(RuntimeException e){
            throw e;
        }catch(Exception e){
            throw new RuntimeException("Token validation failed", e);
        }
    }





    /*Extract the user's auth_id - will be used in service class*/

    public String extractUserId(String token){
        try{
            return extractAllClaims(token).getSubject();

        }catch(Exception e){
            throw new RuntimeException("Failed to retrieve user ID", e);
        }
    }

    public String extractUserEmail(String token){
        try{
            return (String) extractAllClaims(token).getClaimAsString("email");
        }catch(Exception e){
            throw new RuntimeException("Failed to retrieve email", e);
        }
    }



    // public String extractUserRole(String token){
    //     try{
            
    //         return extractAllClaims(token).getClaimAsString("role");

    //     }catch(Exception e){
    //         throw new RuntimeException("Failed to retrieve user role", e);
    //     }
    // }

    public boolean validateToken(String token){
        try{
            extractAllClaims(token);
            return true;
        }catch(Exception e){
            return false;
        }
    }





   


}





    