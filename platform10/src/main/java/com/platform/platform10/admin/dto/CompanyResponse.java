/**
 * 프로그램명: CompanyResponse
 * 기능: 회사 응답 DTO
 * 생성자: devYULSLAB
 * 생성일: 2025-02-28
 * 변경일: 2025-02-28
 */
package com.platform.platform10.admin.dto;

import com.platform.platform10.admin.entity.Company;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class CompanyResponse {
    private String companyId;
    private String companyName;
    private boolean isActive;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static CompanyResponse from(Company company) {
        CompanyResponse response = new CompanyResponse();
        response.setCompanyId(company.getCompanyId());
        response.setCompanyName(company.getCompanyName());
        response.setActive(company.isActive());
        response.setCreatedAt(company.getCreatedAt());
        response.setUpdatedAt(company.getUpdatedAt());
        return response;
    }
}
