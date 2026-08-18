package com.ayushrawat.distributed_lovable.common_lib.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Set;

import static com.ayushrawat.distributed_lovable.common_lib.enums.ProjectPermission.*;


@RequiredArgsConstructor
@Getter
public enum ProjectRole {

    //One Way
    EDITOR(EDIT),
    VIEWER(Set.of(VIEW,VIEW_MEMBERS)),
    OWNER(Set.of(EDIT,VIEW,DELETE,MANAGE_MEMBERS,VIEW_MEMBERS));

    //Another Way
    ProjectRole(ProjectPermission... permission) {
        this.permission = Set.of(permission);
    }

    private final Set<ProjectPermission> permission;
}
