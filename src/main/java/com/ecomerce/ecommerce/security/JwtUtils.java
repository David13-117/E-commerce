/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ecomerce.ecommerce.security;

import com.ecomerce.ecommerce.model.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.util.Date;
import org.springframework.stereotype.Component;

@Component
public class JwtUtils {
    
   private final Key key = Keys.secretKeyFor(SignatureAlgorithm.HS512);
   private final int jwtExpitaionsMs = 180000;
   
   //generar un token
   public String generateToken(User user){
       return Jwts.builder()
               .setSubject(user.getEmail())
               .claim("role", user.getRole())
               .setIssuedAt(new Date())
               .setExpiration(new Date(System.currentTimeMillis()+jwtExpitaionsMs))
               .signWith(key, SignatureAlgorithm.ES512)
               .compact();
               
   }
   
   
   //validar token
   public boolean validatedToken(String token){
       try {
           Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
           return true;
       } catch (Exception e) {
           System.out.println("error al validar el token jwt "+ e.getMessage());
           return false;
       }
   }
   
   
   //obtener reclamaciones del token
   public Claims getClaims(String token){
       return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
   }
    
}
