/**
 * 프로그램명: SiteService
 * 기능: 사이트 서비스
 * 생성자: devYULSLAB
 * 생성일: 2025-02-28
 * 변경일: 2025-02-28
 */
package com.platform.platform10.admin.service;

import com.platform.platform10.admin.entity.Site;
import com.platform.platform10.admin.entity.SiteId;
import com.platform.platform10.admin.repository.SiteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SiteService {

    private final SiteRepository siteRepository;

    public List<Site> findAllSites() {
        return siteRepository.findAll();
    }

    public Optional<Site> findSiteById(SiteId siteId) {
        return siteRepository.findById(siteId);
    }

    @Transactional
    public Site saveSite(Site site) {
        return siteRepository.save(site);
    }

    @Transactional
    public void deleteSite(SiteId siteId) {
        siteRepository.deleteById(siteId);
    }
}
