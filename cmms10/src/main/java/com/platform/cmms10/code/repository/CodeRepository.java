/**
 * 프로그램명: CodeRepository
 * 기능: Code 엔티티에 대한 데이터 접근 리포지토리
 * 생성자: devYULSLAB
 * 생성일: 2025-02-28
 * 변경일: 2025-02-28
 */
package com.platform.cmms10.code.repository;

import com.platform.cmms10.code.entity.Code;
import com.platform.cmms10.code.entity.CodeId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CodeRepository extends JpaRepository<Code, CodeId> {

    /**
     * 특정 회사와 코드 타입에 속하는 모든 코드를 정렬 순서에 따라 조회합니다.
     *
     * @param companyId 회사 ID
     * @param codeType 코드 타입
     * @return 코드 목록
     */
    List<Code> findAllById_CompanyIdAndCodeTypeOrderBySortOrderAsc(Long companyId, String codeType);
}
