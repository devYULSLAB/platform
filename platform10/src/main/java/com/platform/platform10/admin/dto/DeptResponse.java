/**
 * 프로그램명: DeptResponse
 * 기능: 부서 응답 DTO
 * 생성자: devYULSLAB
 * 생성일: 2025-02-28
 * 변경일: 2025-02-28
 */
package com.platform.platform10.admin.dto;

import com.platform.platform10.admin.entity.Dept;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class DeptResponse {
    private String companyId;
    private String siteId;
    private String deptId;
    private String deptName;
    private String parentDeptId;
    private Integer deptLevel;
    private boolean isActive;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static DeptResponse from(Dept dept) {
        DeptResponse response = new DeptResponse();
        response.setCompanyId(dept.getId().getCompanyId());
        response.setSiteId(dept.getId().getSiteId());
        response.setDeptId(dept.getId().getDeptId());
        response.setDeptName(dept.getDeptName());
        if (dept.getParentDept() != null) {
            response.setParentDeptId(dept.getParentDept().getId().getDeptId());
        }
        response.setDeptLevel(dept.getDeptLevel());
        response.setActive(dept.isActive());
        response.setCreatedAt(dept.getCreatedAt());
        response.setUpdatedAt(dept.getUpdatedAt());
        return response;
    }
}
