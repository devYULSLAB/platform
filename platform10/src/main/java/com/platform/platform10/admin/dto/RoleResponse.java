/**
 * 프로그램명: RoleResponse
 * 기능: 역할 응답 DTO
 * 생성자: devYULSLAB
 * 생성일: 2025-02-28
 * 변경일: 2025-02-28
 */
package com.platform.platform10.admin.dto;

import com.platform.platform10.admin.entity.Role;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class RoleResponse {
    private String companyId;
    private String roleId;
    private String roleName;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static RoleResponse from(Role role) {
        RoleResponse response = new RoleResponse();
        response.setCompanyId(role.getId().getCompanyId());
        response.setRoleId(role.getId().getRoleId());
        response.setRoleName(role.getRoleName());
        response.setCreatedAt(role.getCreatedAt());
        response.setUpdatedAt(role.getUpdatedAt());
        return response;
    }
}
