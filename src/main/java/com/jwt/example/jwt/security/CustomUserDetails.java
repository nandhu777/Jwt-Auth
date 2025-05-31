package com.jwt.example.jwt.security;

import com.jwt.example.jwt.model.User;
import com.jwt.example.jwt.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import java.util.List;


@Service
public class CustomUserDetails implements UserDetailsService {
    @Autowired
    private UserRepo userrepo;
    @Override
    public UserDetails loadUserByUsername(String identifer) throws UsernameNotFoundException {
        User user = userrepo.findByUsernameOrEmail(identifer,identifer)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"+identifer));

        return new org.springframework.security.core.userdetails.User(
                user.getUsername() != null ? user.getUsername() : user.getEmail(),
                user.getPassword(),
                List.of(new SimpleGrantedAuthority("ROLE_" + user.getRole().name())));
    }
}
