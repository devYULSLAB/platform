/**
 * 프로그램명: WorkPermitService
 * 기능: 작업허가서 관련 비즈니스 로직을 처리하는 서비스
 * 생성자: devYULSLAB
 * 생성일: 2025-02-28
 * 변경일: 2025-02-28
 */
package com.platform.cmms10.workpermit.service;

import com.platform.cmms10.workpermit.entity.WorkPermit;
import com.platform.cmms10.workpermit.repository.WorkPermitRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class WorkPermitService {

    private final WorkPermitRepository workPermitRepository;

    /**
     * 특정 회사의 작업허가서 목록을 조회합니다.
     *
     * @param companyId 회사 ID
     * @param pageable 페이징 정보
     * @return 작업허가서 페이지
     */
    public Page<WorkPermit> listWorkPermits(Long companyId, Pageable pageable) {
        return workPermitRepository.findById_CompanyId(companyId, pageable);
    }

    /**
     * 특정 작업허가서를 ID로 조회합니다.
     *
     * @param companyId 회사 ID
     * @param permitId 허가서 ID
     * @return Optional<WorkPermit>
     */
    public Optional<WorkPermit> findWorkPermitById(Long companyId, Long permitId) {
        return workPermitRepository.findById_CompanyIdAndId_PermitId(companyId, permitId);
    }

    /**
     * 작업허가서를 저장하거나 업데이트합니다.
     *
     * @param workPermit 저장할 작업허가서 엔티티
     * @param companyId 현재 사용자의 회사 ID (보안 규칙)
     * @param username  현재 사용자 이름 (보안 규칙)
     * @return 저장된 작업허가서 엔티티
     */
    @Transactional
    public WorkPermit saveWorkPermit(WorkPermit workPermit, Long companyId, String username) {
        // Security Rule: Enforce companyId from session
        workPermit.getId().setCompanyId(companyId);

        // Set audit fields
        if (workPermit.getCreatedAt() == null) {
            workPermit.setCreatedBy(username);
        }
        workPermit.setUpdatedBy(username);

        return workPermitRepository.save(workPermit);
    }

    /**
     * 작업허가서를 삭제합니다.
     *
     * @param companyId 회사 ID
     * @param permitId 허가서 ID
     */
    @Transactional
    public void deleteWorkPermit(Long companyId, Long permitId) {
        workPermitRepository.findById_CompanyIdAndId_PermitId(companyId, permitId)
            .ifPresent(workPermitRepository::delete);
    }
}
