package com.ayushrawat.distributed_lovable.account_service.dto.auth;

public record AuthResponse(
        String token,
        UserProfileResponse user
) {

}
