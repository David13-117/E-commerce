/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ecomerce.ecommerce.controller;

import com.ecomerce.ecommerce.security.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin/api")
public class AdminController {
    
    @Autowired
    private JwtUtils jwtUtils;
    
    
    @GetMapping("")
    public ResponseEntity<?> getAdminResource(@RequestHeader("Authorization") String token){
        //extraer el token JWT del encabezado
        String role = jwtUtils.getClaims(token.replace("Bearer ","")).get("role", String.class);
        
        //verificar si el rol es de admin
        if(!"ADMIN".equals(role)){
            return ResponseEntity.status(403).body("Acceso denegado");
        }
        
        return ResponseEntity.ok("Recurso para administradores");
    }
    
    
    
    
}
