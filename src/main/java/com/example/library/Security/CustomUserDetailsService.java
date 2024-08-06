package com.example.library.Security;

import com.example.library.Models.Patron;
import com.example.library.Models.Roles;
import com.example.library.Repository.PatronRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final PatronRepository patronRepository;

    @Autowired
    public CustomUserDetailsService(PatronRepository patronRepository) {
        this.patronRepository = patronRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String identifier) {
        Patron user = patronRepository.findByUsernameOrEmail(identifier, identifier);
        return new User(user.getUsername(), user.getPassword(), mapRolesToAuthorities(user.getRoles()));
    }

    private Collection<GrantedAuthority> mapRolesToAuthorities(List<Roles> roles) {
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
    }

}