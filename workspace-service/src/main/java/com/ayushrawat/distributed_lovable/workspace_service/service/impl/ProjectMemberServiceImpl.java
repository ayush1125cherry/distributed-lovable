package com.ayushrawat.distributed_lovable.workspace_service.service.impl;

import com.ayushrawat.distributed_lovable.common_lib.dto.UserDto;
import com.ayushrawat.distributed_lovable.common_lib.security.AuthUtils;
import com.ayushrawat.distributed_lovable.workspace_service.client.AccountClient;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import com.ayushrawat.distributed_lovable.workspace_service.dto.member.InviteMemberRequest;
import com.ayushrawat.distributed_lovable.workspace_service.dto.member.MemberResponse;
import com.ayushrawat.distributed_lovable.workspace_service.dto.member.UpdateMemberRoleRequest;
import com.ayushrawat.distributed_lovable.workspace_service.entity.Project;
import com.ayushrawat.distributed_lovable.workspace_service.entity.ProjectMember;
import com.ayushrawat.distributed_lovable.workspace_service.entity.ProjectMemberId;
import com.ayushrawat.distributed_lovable.workspace_service.mapper.ProjectMemberMapper;
import com.ayushrawat.distributed_lovable.workspace_service.repository.ProjectMemberRepository;
import com.ayushrawat.distributed_lovable.workspace_service.repository.ProjectRepository;
import com.ayushrawat.distributed_lovable.workspace_service.service.ProjectMemberService;

import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.time.Instant;
import java.util.List;

@Service
@FieldDefaults(makeFinal = true,level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
@Transactional
public class ProjectMemberServiceImpl implements ProjectMemberService {

    ProjectMemberRepository projectMemberRepository;
    ProjectRepository projectRepository;
    ProjectMemberMapper projectMemberMapper;
    AuthUtils authUtils;
    AccountClient accountClient;


    @Override
    @PreAuthorize("@security.canViewMembers(#projectId)")
    public List<MemberResponse> getProjectMembers(Long projectId) {


        return projectMemberRepository.findByIdProjectId(projectId)
                .stream()
                .map(projectMemberMapper::toProjectMemberResponseFromMember)
                .toList();

    }

    @Override
    @PreAuthorize("@security.canManageMember(#projectId)")
    public MemberResponse inviteMember(Long projectId, InviteMemberRequest request) {
        Long userId = authUtils.getCurrentUserId();
        Project project = getAccessibleProjectById(projectId,userId);



        UserDto invitee = accountClient.getUserByEmail(request.username()).orElseThrow(() -> new RuntimeException(
                        "User not found: " + request.username()
                ));

        if (!invitee.Id().equals(userId)) {
            ProjectMemberId projectMemberId = new ProjectMemberId(projectId, invitee.Id());
            if (projectMemberRepository.existsById(projectMemberId)) {
                throw new RuntimeException("cannot invite once again");
            }

            ProjectMember member = ProjectMember.builder()
                    .id(projectMemberId)
                    .project(project)
                    .projectRole(request.role())
                    .invitedAt(Instant.now())
                    .build();

            projectMemberRepository.save(member);

            return projectMemberMapper.toProjectMemberResponseFromMember(member);
        } else {
            throw new RuntimeException("Not allowed to invite");
        }

    }

    @Override
    @PreAuthorize("@security.canManageMember(#projectId)")
    public MemberResponse updateMemberRole(Long projectId, Long memberId, UpdateMemberRoleRequest request) {
        Long userId = authUtils.getCurrentUserId();
        Project project = getAccessibleProjectById(projectId,userId);

        ProjectMemberId projectMemberId = new ProjectMemberId(projectId, memberId);
        ProjectMember projectMember= projectMemberRepository.findById(projectMemberId).orElseThrow();
        projectMember.setProjectRole(request.role());

        projectMemberRepository.save(projectMember);


        return projectMemberMapper.toProjectMemberResponseFromMember(projectMember);
    }

    @Override
    @PreAuthorize("@security.canManageMember(#projectId)")
    public void removeProjectMember(Long projectId, Long memberId) {
        Long userId = authUtils.getCurrentUserId();
        Project project = getAccessibleProjectById(projectId,userId);

        ProjectMemberId projectMemberId = new ProjectMemberId(projectId, memberId);
        if(!projectMemberRepository.existsById(projectMemberId)){
            throw new RuntimeException("Project Member not found in project");
        }

        projectMemberRepository.deleteById(projectMemberId);

    }


    //Internal functions...
    public Project getAccessibleProjectById(Long projectId,Long userId){
        return  projectRepository.findAccessibleProjectById(projectId, userId).orElseThrow();

    }
}
