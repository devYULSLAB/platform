/**
 * 프로그램명: Site
 * 기능: 사이트(사업장) 엔티티
 * 생성자: devYULSLAB
 * 생성일: 2025-02-28
 * 변경일: 2025-02-28
 */
package com.platform.common.security.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "site")
@Getter
@Setter
public class Site {

    @EmbeddedId
    private SiteId id;

    @Column(name = "site_name", nullable = false)
    private String siteName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id", referencedColumnName = "company_id", insertable = false, updatable = false)
    private Company company;
}
