package com.project.miniReddit.service;

import com.project.miniReddit.Util.JwtUtil;
import com.project.miniReddit.dto.AuthenticationResponse;
import com.project.miniReddit.dto.LoginRequest;
import com.project.miniReddit.dto.RefreshTokenRequest;
import com.project.miniReddit.dto.SignupRequest;
import com.project.miniReddit.entity.User;
import com.project.miniReddit.exception.SpringRedditException;
import com.project.miniReddit.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Date;
import java.util.Optional;

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
    @Autowired
    private RefreshTokenService refreshTokenService;

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

        //return new Authentication token along with username and refresh token
        AuthenticationResponse authenticationResponse = AuthenticationResponse.builder()
                .authenticationToken(authenticationToken)
                .refreshToken(refreshTokenService.generateRefreshToken().getRefreshToken())
                .expiresAt(Instant.now().plusMillis(1000 * 60 * 60 * 10))
                .username(loginRequest.getUsername())
                .build();

        return authenticationResponse;
    }

    public User getCurrentUser(){
        org.springframework.security.core.userdetails.User principal = (org.springframework.security.core.userdetails.User)
                SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Optional<User> user = userRepository.findByUsername(principal.getUsername());
        return user.orElseThrow(()-> new UsernameNotFoundException("User " + principal.getUsername() + "Not found"));
    }

    public AuthenticationResponse refreshJWTToken(RefreshTokenRequest refreshTokenRequest) {
        refreshTokenService.validateRefreshToken(refreshTokenRequest.getRefreshToken());
        User user = this.getCurrentUser();
        UserDetails userDetails = userDetailsService.loadUserByUsername(user.getUsername());
        String newJWT = jwtUtil.generateToken(userDetails);
        return AuthenticationResponse.builder()
                .authenticationToken(newJWT)
                .refreshToken(refreshTokenRequest.getRefreshToken())
                .expiresAt(Instant.now().plusMillis(1000 * 60 * 60 * 10))
                .username(user.getUsername())
                .build();
    }

}
