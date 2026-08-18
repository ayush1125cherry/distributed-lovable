package com.ayushrawat.distributed_lovable.account_service.service;


import com.ayushrawat.distributed_lovable.account_service.dto.auth.AuthResponse;
import com.ayushrawat.distributed_lovable.account_service.dto.auth.LoginRequest;
import com.ayushrawat.distributed_lovable.account_service.dto.auth.SignupRequest;

public interface AuthService {
    AuthResponse signup(SignupRequest request);

    AuthResponse login(LoginRequest request);
}
