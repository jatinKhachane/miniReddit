package com.project.miniReddit.service;

import com.project.miniReddit.entity.User;
import com.project.miniReddit.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Optional;

import static java.util.Collections.singletonList;

@Component
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> userOptional = userRepository.findByUsername(username);
        User user = userOptional.orElseThrow(()-> new UsernameNotFoundException("No user with name : " + username));

        //return User class object (provided by Spring)
       return new org.springframework.security.core.userdetails.User(
                                                                    user.getUsername(),
                                                                    user.getPassword(),
                                                                   true,
                                                                    true,
                                                                    true,
                                                                    true,
                                                                    getAuthorities("USER"));
    }

    private Collection<? extends GrantedAuthority> getAuthorities(String role) {
        return singletonList(new SimpleGrantedAuthority(role));
    }
}
