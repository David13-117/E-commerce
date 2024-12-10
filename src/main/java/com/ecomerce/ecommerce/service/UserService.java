/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ecomerce.ecommerce.service;

import com.ecomerce.ecommerce.model.User;
import com.ecomerce.ecommerce.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.*;


@Service
public class UserService {
    
    @Autowired
    private UserRepository userRepository;
    
    //obtener todos los usuarios
    public List<User> getAllUsers(){
        return userRepository.findAll();
        
    }
    
    //buscar los usuarios por id
    public Optional<User> getUserById(String id){
        return userRepository.findById(id);
        
    }
    
    //actualizar un usuario
    public User updateUser(String id, User updatedUser){
        
        Optional<User> userOptional = userRepository.findById(id);
        
        if(userOptional.isPresent()){
            User existingUser = userOptional.get();
            existingUser.setName(updatedUser.getName());
            existingUser.setEmail(updatedUser.getEmail());
            existingUser.setAddress(updatedUser.getAddress());
            existingUser.setRole(updatedUser.getRole());
            return userRepository.save(existingUser);
            
        }else{
            throw new IllegalArgumentException("Usuario no encontrado");
        }
    }
    
    //eliminar usuario
    
    public void deteleUser (String id){
        if(!userRepository.existsById(id)){
            throw new IllegalArgumentException("Usuario no encontrado");
        }
        userRepository.deleteById(id);
        System.out.println("Eliminado con exito");
    }
    
}
