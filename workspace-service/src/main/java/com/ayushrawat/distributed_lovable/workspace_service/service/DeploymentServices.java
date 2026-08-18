package com.ayushrawat.distributed_lovable.workspace_service.service;


import com.ayushrawat.distributed_lovable.workspace_service.dto.project.DeployResponse;
import org.jspecify.annotations.Nullable;

public interface DeploymentServices {
    @Nullable DeployResponse deploy(Long projectId);

}
