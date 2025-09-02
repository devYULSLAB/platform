/**
 * 프로그램명: CompanyRequest
 * 기능: 회사 생성/수정 요청 DTO
 * 생성자: devYULSLAB
 * 생성일: 2025-02-28
 * 변경일: 2025-02-28
 */
package com.platform.platform10.admin.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CompanyRequest {
    private String companyId;
    private String companyName;
    private Boolean active;
}
