package com.project.miniReddit.service;

import com.project.miniReddit.Util.JwtUtil;
import com.project.miniReddit.dto.AuthenticationResponse;
import com.project.miniReddit.dto.LoginRequest;
import com.project.miniReddit.dto.SignupRequest;
import com.project.miniReddit.entity.User;
import com.project.miniReddit.exception.SpringRedditException;
import com.project.miniReddit.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private JwtUtil jwtUtil;

    public void signup(SignupRequest signupRequest){
        User user = User.builder()
                .username(signupRequest.getUsername())
                .email(signupRequest.getEmail())
                .password(encodePass(signupRequest.getPassword()))
                .created(Instant.now())
                .enabled(false)
                .build();

        userRepository.save(user);
    }

    public String encodePass(String pass){
        return passwordEncoder.encode(pass);
    }

    public AuthenticationResponse login(LoginRequest loginRequest) throws Exception{

        //authenticate from AuthenticationManager
        System.out.println(loginRequest.getUsername());
        Authentication authenticate = null;
        try{
            authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(),loginRequest.getPassword()));
        }catch(Exception e){
            throw new Exception(e.getMessage());
        }

        // enable the user
        User user = userRepository.findByUsername(loginRequest.getUsername()).orElseThrow(() -> new SpringRedditException("User Not Found with id - " + loginRequest.getUsername()));
        user.setEnabled(true);
        userRepository.save(user);

        //generate the Signed JWT token
        UserDetails userDetails = userDetailsService.loadUserByUsername(loginRequest.getUsername());
        String authenticationToken = jwtUtil.generateToken(userDetails);

        //return new Authentication token along with username
        return new AuthenticationResponse(authenticationToken, loginRequest.getUsername());
    }
}
