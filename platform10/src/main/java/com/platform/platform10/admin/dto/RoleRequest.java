/**
 * 프로그램명: RoleRequest
 * 기능: 역할 생성/수정 요청 DTO
 * 생성자: devYULSLAB
 * 생성일: 2025-02-28
 * 변경일: 2025-02-28
 */
package com.platform.platform10.admin.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RoleRequest {
    private String companyId;
    private String roleId;
    private String roleName;
}
