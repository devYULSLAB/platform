/**
 * 프로그램명: UserAccountRequest
 * 기능: 사용자 계정 생성/수정 요청 DTO
 * 생성자: devYULSLAB
 * 생성일: 2025-02-28
 * 변경일: 2025-02-28
 */
package com.platform.platform10.admin.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserAccountRequest {
    private String companyId;
    private String userId;
    private String username;
    private String password;
    private String siteId;
    private String deptId;
    private String phone;
    private String email;
    private Boolean active;
}
