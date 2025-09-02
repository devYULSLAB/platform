/**
 * 프로그램명: DeptRequest
 * 기능: 부서 생성/수정 요청 DTO
 * 생성자: devYULSLAB
 * 생성일: 2025-02-28
 * 변경일: 2025-02-28
 */
package com.platform.platform10.admin.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DeptRequest {
    private String companyId;
    private String siteId;
    private String deptId;
    private String deptName;
    private String parentDeptId;
    private Integer deptLevel;
    private Boolean active;
}
