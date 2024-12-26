package com.projectea.projectea.domain.impl.auth;

public interface AuthenticationService {
    AuthenticationResponse login(LoginRequest request);
    AuthenticationResponse register(RegisterRequest request);
}
