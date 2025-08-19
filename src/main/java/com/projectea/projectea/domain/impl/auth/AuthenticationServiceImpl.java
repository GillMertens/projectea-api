package com.projectea.projectea.domain.impl.auth;

import com.projectea.projectea.configuration.security.JwtTokenProvider;
import com.projectea.projectea.domain.impl.user.entities.Role;
import com.projectea.projectea.domain.impl.user.entities.User;
import com.projectea.projectea.domain.impl.user.repositories.UserRepository;
import com.projectea.projectea.exceptions.EmailAlreadyExistsException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationManager authenticationManager;

    /**
     * Authenticates a user with email and password, then generates a JWT token.
     * 
     * @param request The login request containing email and password
     * @return AuthenticationResponse containing the JWT token
     * @throws org.springframework.security.core.AuthenticationException if authentication fails
     */
    @Override
    public AuthenticationResponse login(LoginRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        var user = userRepository.findByEmail(request.getEmail()).orElseThrow();
        var jwtToken = jwtTokenProvider.generateToken(user);
        return AuthenticationResponse.builder()
            .token(jwtToken)
            .build();
    }

    /**
     * Registers a new user with the provided information and generates a JWT token.
     * Validates that the email is not already in use before creating the user.
     * 
     * @param request The registration request containing user details
     * @return AuthenticationResponse containing the JWT token
     * @throws EmailAlreadyExistsException if the email is already registered
     */
    @Override
    public AuthenticationResponse register(RegisterRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new EmailAlreadyExistsException();
        }
        var user = User.builder()
            .firstName(request.getFirstName())
            .lastName(request.getLastName())
            .email(request.getEmail())
            .password(passwordEncoder.encode(request.getPassword()))
            .phoneNumber(request.getPhoneNumber())
            .address(request.getAddress())
            .role(Role.USER)
            .build();
        userRepository.save(user);
        var jwtToken = jwtTokenProvider.generateToken(user);
        return AuthenticationResponse.builder()
            .token(jwtToken)
            .build();
    }

}
