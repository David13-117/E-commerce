/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ecomerce.ecommerce.security;

import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 *
 * @author nefor
 */
public class JwtAuthenticationFilter extends OncePerRequestFilter{
    
    @Autowired
    private JwtUtils JwtUtils;
    
    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        
        //obtener el token Jwt del encabezado de la autorización
        String authHeader = request.getHeader("authorization");
                if(authHeader == null || !authHeader.startsWith("Bearer")){
                    filterChain.doFilter(request, response);
                    return;
                }
                
                String jwtToken = authHeader.substring(7);//remover el Bearer
                
                try {
                    //validar el token y extraer el correo electronico del usuario
                    Claims claims = JwtUtils.getClaims(jwtToken);
                    String UserEmail = claims.getSubject();
                    
                    if(UserEmail != null && SecurityContextHolder.getContext().getAuthentication()==null){
                        //cargar los detalles del usuario desde la base de datos
                        UserDetails userDetails = userDetailsService.loadUserByUsername(UserEmail);
                        
                        //validar el token
                        if(JwtUtils.validatedToken(jwtToken)){
                            // establecer la autenticacion en el contexto de seguridad
                            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                            userDetails, null, userDetails.getAuthorities());
                            
                            authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                            
                            SecurityContextHolder.getContext().setAuthentication(authToken);
                        }
                    }
        } catch (Exception e) {
                    System.out.println("Error al validar el token JWT"+e.getMessage());
        }
                
                //continuar con la cadena de filtros
                filterChain.doFilter(request, response);
    }
    
    
    
}
