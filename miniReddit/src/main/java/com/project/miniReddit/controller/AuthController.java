package com.project.miniReddit.controller;

import com.project.miniReddit.dto.AuthenticationResponse;
import com.project.miniReddit.dto.LoginRequest;
import com.project.miniReddit.dto.SignupRequest;
import com.project.miniReddit.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity signup(@RequestBody SignupRequest signupRequest){
        authService.signup(signupRequest);
        return new ResponseEntity(HttpStatus.OK);
    }

    @PostMapping("/login")
    public AuthenticationResponse login(@RequestBody LoginRequest loginRequest) throws Exception {
        return authService.login(loginRequest);
    }
}
