package com.ayushrawat.distributed_lovable.workspace_service.dto.member;

import com.ayushrawat.distributed_lovable.common_lib.enums.ProjectRole;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record InviteMemberRequest(
        @NotNull @NotBlank String username,
        @NotNull ProjectRole role
) {
}
