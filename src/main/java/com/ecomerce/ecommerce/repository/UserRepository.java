/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.ecomerce.ecommerce.repository;

import com.ecomerce.ecommerce.model.User;
import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;


public interface UserRepository extends MongoRepository<User, String>{
    
    Optional<User> findByEmail(String email);
    boolean existsByEmail(String email);
    
    
            
}
