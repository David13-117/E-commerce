/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ecomerce.ecommerce.security;

import com.ecomerce.ecommerce.model.User;
import com.ecomerce.ecommerce.repository.UserRepository;
import java.util.Collections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService{
    
    @Autowired
    private UserRepository UserRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        //buscar usuario por email en la base de datos
        User user = UserRepository.findByEmail(email)
                .orElseThrow(()-> new UsernameNotFoundException("Usuaerio no encontrado con el correo"));
        
        //crear un objeto userDetails para que el Spring Security pueda utilizar
        return new org.springframework.security.core.userdetails.User(
            user.getEmail(),
            user.getPassword(),
                Collections.emptyList()//autoridades (roles) puedes añadirlos si es necesarios
        );
    }
    
    
    
}
