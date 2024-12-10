/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ecomerce.ecommerce.controller;

import com.ecomerce.ecommerce.dto.LoginRequest;
import com.ecomerce.ecommerce.dto.RegisterRequest;
import com.ecomerce.ecommerce.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    
        @Autowired
    private AuthService authService;
 
    //endpoint para registrar un usuario
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody RegisterRequest request) {
 
        try {
            String message = authService.registerUser(
                    request.getName(),
                    request.getEmail(),
                    request.getPhone(),
                    request.getPassword(),
                    request.getAddress()
            );
            return ResponseEntity.ok(message);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("error interno del serfvidor");
 
        }
 
    }
    
    //enpoint para autenticar un usuario(Login)
    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest request){
        try {
            String token = authService.authenticateUser(request.getEmail(), request.getPassword());
            return ResponseEntity.ok(token);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("error interno del serfvidor");
 
        }
    }
    
}
