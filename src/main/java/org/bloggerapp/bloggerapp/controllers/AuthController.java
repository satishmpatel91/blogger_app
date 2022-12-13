package org.bloggerapp.bloggerapp.controllers;

import org.bloggerapp.bloggerapp.exceptions.ApiException;
import org.bloggerapp.bloggerapp.payloads.JWTRequest;
import org.bloggerapp.bloggerapp.payloads.JWTResponse;
import org.bloggerapp.bloggerapp.payloads.UserDto;
import org.bloggerapp.bloggerapp.security.JWTTokenHelper;
import org.bloggerapp.bloggerapp.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private JWTTokenHelper jwtTokenHelper;

    private UserDetailsService userDetailsService;

    private UserService userService;

    private AuthenticationManager authenticationManager;


    @PostMapping("/login")
    public ResponseEntity<JWTResponse> doLogin(@RequestBody JWTRequest jwtRequest) {
        authenticate(jwtRequest.getEmail(), jwtRequest.getPassword());
        UserDetails userDetails = userDetailsService.loadUserByUsername(jwtRequest.getEmail());
        String generateToken = jwtTokenHelper.generateToken(userDetails);
        JWTResponse jwtResponse = new JWTResponse();
        jwtResponse.setToken(generateToken);
        return new ResponseEntity<>(jwtResponse, HttpStatus.CREATED);
    }

    @PostMapping("/register")
    public ResponseEntity<UserDto> doRegister(@RequestBody UserDto userDto) {
        UserDto registerNewUser = userService.registerNewUser(userDto);
        return new ResponseEntity<>(registerNewUser,HttpStatus.CREATED);
    }


    private void authenticate(String email, String password) {
        try {
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(email, password);
            authenticationManager.authenticate(authenticationToken);
        } catch (BadCredentialsException exception) {
            throw new ApiException("Invalid Username and password");
        }

    }

    @Autowired
    public void setUserDetailsService(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Autowired
    public void setAuthenticationManager(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }
@Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setJwtTokenHelper(JWTTokenHelper jwtTokenHelper) {
        this.jwtTokenHelper = jwtTokenHelper;
    }
}
