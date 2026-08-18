package com.ayushrawat.distributed_lovable.workspace_service.mapper;


import com.ayushrawat.distributed_lovable.workspace_service.dto.member.MemberResponse;
import com.ayushrawat.distributed_lovable.workspace_service.entity.ProjectMember;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


@Mapper(componentModel = "spring")
public interface ProjectMemberMapper {



    //Nested Mapping
    @Mapping(target = "userId", source = "id.userId")
    MemberResponse toProjectMemberResponseFromMember(ProjectMember projectMember);

}


