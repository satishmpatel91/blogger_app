package org.bloggerapp.bloggerapp.security;

import org.bloggerapp.bloggerapp.entities.Users;
import org.bloggerapp.bloggerapp.exceptions.ResourceNotFoundException;
import org.bloggerapp.bloggerapp.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Users users = userRepository.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException("User", "Email Id", email));
        return users;
    }

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
}
