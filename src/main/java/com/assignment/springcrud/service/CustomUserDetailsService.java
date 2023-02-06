package com.assignment.springcrud.service;

import com.assignment.springcrud.CustomUserDetails;
import com.assignment.springcrud.entity.User;
import com.assignment.springcrud.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository repository;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = repository.findByUsername(username);
//        System.out.println(user);
        if(user == null)
            throw new UsernameNotFoundException("User not found");
        return new CustomUserDetails(user);
    }
}
