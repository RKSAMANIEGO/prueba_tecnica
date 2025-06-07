package com.prueba.tecnica.nttdata.gestion_employee.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.prueba.tecnica.nttdata.gestion_employee.model.CUser;
import com.prueba.tecnica.nttdata.gestion_employee.repository.IUserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CUserDetailsServiceImpl implements UserDetailsService{

    private final IUserRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        
        CUser userFound = repository.findByEmail(username).orElseThrow(()->new UsernameNotFoundException("El Usuario "+username+" No Existe."));
        
        List<SimpleGrantedAuthority> authority= new ArrayList<>();

        authority.add(new SimpleGrantedAuthority("ROLE_"+userFound.getRole().toString()));

       return new User(userFound.getEmail(), userFound.getPassword(), userFound.getIsEnabled(), true, true, true, authority);
    }
    
}
