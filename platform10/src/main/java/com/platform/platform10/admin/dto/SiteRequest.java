/**
 * 프로그램명: SiteRequest
 * 기능: 사이트 생성/수정 요청 DTO
 * 생성자: devYULSLAB
 * 생성일: 2025-02-28
 * 변경일: 2025-02-28
 */
package com.platform.platform10.admin.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SiteRequest {
    private String companyId;
    private String siteId;
    private String siteName;
    private Boolean active;
}
