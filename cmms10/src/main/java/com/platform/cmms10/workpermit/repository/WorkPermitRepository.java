/**
 * 프로그램명: WorkPermitRepository
 * 기능: WorkPermit 엔티티에 대한 데이터 접근 리포지토리
 * 생성자: devYULSLAB
 * 생성일: 2025-02-28
 * 변경일: 2025-02-28
 */
package com.platform.cmms10.workpermit.repository;

import com.platform.cmms10.workpermit.entity.WorkPermit;
import com.platform.cmms10.workpermit.entity.WorkPermitId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WorkPermitRepository extends JpaRepository<WorkPermit, WorkPermitId> {

    /**
     * 회사 ID를 기준으로 작업허가서 목록을 페이징하여 조회합니다.
     *
     * @param companyId 회사 ID
     * @param pageable 페이징 정보
     * @return 페이징된 작업허가서 목록
     */
    @Query("SELECT wp FROM WorkPermit wp WHERE wp.id.companyId = :companyId")
    Page<WorkPermit> findById_CompanyId(@Param("companyId") Long companyId, Pageable pageable);

    /**
     * 회사 ID와 허가서 ID로 특정 작업허가서를 조회합니다.
     *
     * @param companyId 회사 ID
     * @param permitId 허가서 ID
     * @return Optional<WorkPermit>
     */
    Optional<WorkPermit> findById_CompanyIdAndId_PermitId(Long companyId, Long permitId);
}
