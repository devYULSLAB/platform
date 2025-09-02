/**
 * 프로그램명: SiteId
 * 기능: Site 엔티티의 복합 기본 키 클래스
 * 생성자: devYULSLAB
 * 생성일: 2025-02-28
 * 변경일: 2025-02-28
 */
package com.platform.common.security.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class SiteId implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(name = "company_id")
    private Long companyId;

    @Column(name = "site_id")
    private Long siteId;
}
