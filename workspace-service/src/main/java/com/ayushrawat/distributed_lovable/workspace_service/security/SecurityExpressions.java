package com.ayushrawat.distributed_lovable.workspace_service.security;


import com.ayushrawat.distributed_lovable.common_lib.enums.ProjectPermission;
import com.ayushrawat.distributed_lovable.common_lib.security.AuthUtils;
import com.ayushrawat.distributed_lovable.workspace_service.repository.ProjectMemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component("security")
@RequiredArgsConstructor

public class SecurityExpressions {
    private final ProjectMemberRepository projectMemberRepository;
    private final AuthUtils authUtils;


    public boolean hasPermission(Long projectId, ProjectPermission projectPermission){
        Long userId = authUtils.getCurrentUserId();

        System.out.println("UserId = " + userId);
        System.out.println("ProjectId = " + projectId);

        var role = projectMemberRepository.findRoleByProjectIdAndUserId(projectId, userId);

        System.out.println("Role = " + role);

        return role
                .map(r -> r.getPermission().contains(projectPermission))
                .orElse(false);
    }

    public boolean canViewProject(Long projectId){
        return hasPermission(projectId, ProjectPermission.VIEW);
    }



    public boolean canEditProject(Long projectId){
        return hasPermission(projectId, ProjectPermission.EDIT);
    }

    public boolean canDeleteProject(Long projectId){
        return hasPermission(projectId, ProjectPermission.DELETE);
    }

    public boolean canViewMembers(Long projectId){
        return hasPermission(projectId, ProjectPermission.VIEW_MEMBERS);
    }
    public boolean canManageMember(Long projectId){
        return hasPermission(projectId, ProjectPermission.MANAGE_MEMBERS);
    }
}
