/**
 * 프로그램명: SiteResponse
 * 기능: 사이트 응답 DTO
 * 생성자: devYULSLAB
 * 생성일: 2025-02-28
 * 변경일: 2025-02-28
 */
package com.platform.platform10.admin.dto;

import com.platform.platform10.admin.entity.Site;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class SiteResponse {
    private String companyId;
    private String siteId;
    private String siteName;
    private boolean isActive;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static SiteResponse from(Site site) {
        SiteResponse response = new SiteResponse();
        response.setCompanyId(site.getId().getCompanyId());
        response.setSiteId(site.getId().getSiteId());
        response.setSiteName(site.getSiteName());
        response.setActive(site.isActive());
        response.setCreatedAt(site.getCreatedAt());
        response.setUpdatedAt(site.getUpdatedAt());
        return response;
    }
}
