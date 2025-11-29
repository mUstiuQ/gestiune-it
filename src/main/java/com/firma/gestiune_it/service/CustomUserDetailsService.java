package com.firma.gestiune_it.service;

import com.firma.gestiune_it.model.Utilizator;
import com.firma.gestiune_it.repository.UtilizatorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UtilizatorRepository utilizatorRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 1.cautam utilizatorul in baza noastra de date
        Utilizator user = utilizatorRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Utilizatorul nu exista!"));

        // 2. il transformam intr-un obiect pe care Spring Security il intelege (UserDetails)
        return new User(
                user.getUsername(),
                user.getParola(),
                Collections.singletonList(new SimpleGrantedAuthority(user.getRol()))
        );
    }
}