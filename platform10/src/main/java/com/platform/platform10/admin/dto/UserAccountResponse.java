/**
 * 프로그램명: UserAccountResponse
 * 기능: 사용자 계정 응답 DTO
 * 생성자: devYULSLAB
 * 생성일: 2025-02-28
 * 변경일: 2025-02-28
 */
package com.platform.platform10.admin.dto;

import com.platform.platform10.admin.entity.UserAccount;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class UserAccountResponse {
    private String companyId;
    private String userId;
    private String username;
    private String siteId;
    private String deptId;
    private String phone;
    private String email;
    private boolean isActive;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static UserAccountResponse from(UserAccount userAccount) {
        UserAccountResponse response = new UserAccountResponse();
        response.setCompanyId(userAccount.getId().getCompanyId());
        response.setUserId(userAccount.getId().getUserId());
        response.setUsername(userAccount.getUsername());
        if (userAccount.getSite() != null) {
            response.setSiteId(userAccount.getSite().getId().getSiteId());
        }
        if (userAccount.getDept() != null) {
            response.setDeptId(userAccount.getDept().getId().getDeptId());
        }
        response.setPhone(userAccount.getPhone());
        response.setEmail(userAccount.getEmail());
        response.setActive(userAccount.isActive());
        response.setCreatedAt(userAccount.getCreatedAt());
        response.setUpdatedAt(userAccount.getUpdatedAt());
        return response;
    }
}
