/**
 * 프로그램명: SiteRepository
 * 기능: 사이트 리포지토리
 * 생성자: devYULSLAB
 * 생성일: 2025-02-28
 * 변경일: 2025-02-28
 */
package com.platform.platform10.admin.repository;

import com.platform.platform10.admin.entity.Site;
import com.platform.platform10.admin.entity.SiteId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SiteRepository extends JpaRepository<Site, SiteId> {
}
