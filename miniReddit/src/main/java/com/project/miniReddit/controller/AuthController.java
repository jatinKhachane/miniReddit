package com.project.miniReddit.controller;

import com.project.miniReddit.dto.AuthenticationResponse;
import com.project.miniReddit.dto.LoginRequest;
import com.project.miniReddit.dto.SignupRequest;
import com.project.miniReddit.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/auth/signup")
    public ResponseEntity signup(@RequestBody SignupRequest signupRequest){
        authService.signup(signupRequest);
        return new ResponseEntity(HttpStatus.OK);
    }

    @PostMapping("/auth/login")
    public AuthenticationResponse login(@RequestBody LoginRequest loginRequest) throws Exception {
        return authService.login(loginRequest);
    }

    @GetMapping("/home-page")
    public void homePage(){

    }
}
