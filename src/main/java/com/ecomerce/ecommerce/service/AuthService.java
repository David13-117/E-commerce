/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ecomerce.ecommerce.service;

import com.ecomerce.ecommerce.model.User;
import com.ecomerce.ecommerce.repository.UserRepository;
import com.ecomerce.ecommerce.security.JwtUtils;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;



@Service
public class AuthService {
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Autowired
    private JwtUtils jwtUtils;
    
    // registrar usuario
    public String registerUser (String name, String email, String phone, String password, String address){
        //verificar si el correo ya esta registrado
        if(userRepository.existsByEmail(email)){
            throw new IllegalArgumentException("el correo ya esta registrado...");
        }
        
        //crear usuario
        User newUser = new User();
        newUser.setName(name);
        newUser.setEmail(email);
        newUser.setPhone(phone);
        newUser.setPassword(passwordEncoder.encode(password));
        newUser.setAddress(address);
        newUser.setRole("USER");//asignar rol predeterminado
        
        //guardar usuario
        userRepository.save(newUser);
        return "Usuario registrado con exito";
    }
    
    //autenticar usuario
    public String authenticateUser(String email, String password){
        
        Optional<User> useroptional = userRepository.findByEmail(email);
        if(!useroptional.isPresent()){
            throw new IllegalArgumentException("Credenciales incorrectas");
        }
        
        User user = useroptional.get();
        System.out.println("Usuario encontrado "+user.getName());
        
        
        //verificar la contraseña
        if(!passwordEncoder.matches(password, user.getPassword())){
            throw new IllegalArgumentException("Credenciales incorrectas");
        }
        
        //generar token JWT
        try {
            System.out.println("Generando token JWT...");
            String token = jwtUtils.generateToken(user);
            System.out.println("Token generado con exito "+token);
            return token;
        } catch (Exception e) {
            System.out.println("Error al generar el token "+e.getMessage());
            throw new RuntimeException("error inetrno al generar el token");
        }
        
    }
    
    
}
